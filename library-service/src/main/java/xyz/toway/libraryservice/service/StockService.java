package xyz.toway.libraryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.StockEntity;
import xyz.toway.libraryservice.model.StockModel;
import xyz.toway.libraryservice.repository.LibraryRepository;
import xyz.toway.libraryservice.repository.LibraryStockRepository;
import xyz.toway.libraryservice.repository.StockRepository;
import xyz.toway.shared.exception.WrongParamsException;
import xyz.toway.shared.model.SharedSaleModel;

import java.util.List;
import java.util.Objects;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final LibraryRepository libraryRepository;
    private final LibraryStockRepository libraryStockRepository;
    private final ProxyService proxyService;

    public StockService(@Autowired StockRepository stockRepository,
                        @Autowired LibraryRepository libraryRepository,
                        @Autowired LibraryStockRepository libraryStockRepository,
                        @Autowired ProxyService proxyService) {
        this.stockRepository = stockRepository;
        this.libraryRepository = libraryRepository;
        this.libraryStockRepository = libraryStockRepository;
        this.proxyService = proxyService;
    }

    public List<StockEntity> getAll() {
        return stockRepository.findAll();
    }

    public StockModel getById(Long id) {
        var item = stockRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("No stock item with id=" + id));
        if (Objects.isNull(item.getLibrary())) {
            throw new WrongParamsException("No library found.");
        }
        return new StockModel(item.getId(), item.getLibrary().getId(), item.getBookId(), item.getQuantity(), item.getPrice());
    }

    public StockEntity save(StockModel model) {
        LibraryEntity library = libraryRepository.findById(model.libraryId())
                .orElseThrow(() -> new WrongParamsException("No library with id=" + model.libraryId()));

        //check book
        if (!proxyService.checkBookExists(model.bookId())) {
            throw new WrongParamsException("No book with id=" + model.bookId());
        }

        StockEntity entity = new StockEntity(model.bookId(), model.quantity(), model.price());
        entity.setLibrary(library);

        return stockRepository.save(entity);
    }

    public StockEntity update(StockModel item, Long id) {
        StockEntity stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("No stock item with id=" + id));
        LibraryEntity libraryEntity = libraryRepository.findById(item.libraryId())
                .orElseThrow(() -> new WrongParamsException("No library with id=" + item.libraryId()));

        //check book
        if (!proxyService.checkBookExists(item.bookId())) {
            throw new WrongParamsException("No book with id=" + item.bookId());
        }

        //refresh stock item data
        stockEntity.setLibrary(libraryEntity);
        stockEntity.setBookId(item.bookId());
        stockEntity.setQuantity(item.quantity());
        stockEntity.setPrice(item.price());
        stockEntity.setId(id);
        return stockRepository.save(stockEntity);
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }

    public boolean checkBeforeSale(Long libraryId, Long bookId, Integer quantity) {
        var stockOptional = libraryStockRepository.findFirstByLibraryIdAndBookId(libraryId, bookId);
        return stockOptional.isPresent() && stockOptional.get().getQuantity() >= quantity;
    }

    /**
     * Used by RabbitMQ
     */
    public void adjustStockItem(SharedSaleModel model) {
        var item = stockRepository.findByLibraryIdAndBookId(model.libraryId(), model.bookId())
                .orElseThrow(() -> new WrongParamsException("No stock item."));
        if (item.getQuantity() >= model.quantity()) {
            item.setQuantity(item.getQuantity() - model.quantity());
            stockRepository.save(item);
        } else {
            throw new WrongParamsException("Insufficient quantity of books. [id=%d, quantity=%d]".formatted(model.bookId(), model.quantity()));
        }
    }
}
