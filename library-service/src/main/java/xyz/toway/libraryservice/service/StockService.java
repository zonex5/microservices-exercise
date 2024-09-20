package xyz.toway.libraryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.StockEntity;
import xyz.toway.libraryservice.model.StockModel;
import xyz.toway.libraryservice.proxy.BookServiceProxy;
import xyz.toway.libraryservice.repository.LibraryRepository;
import xyz.toway.libraryservice.repository.LibraryStockRepository;
import xyz.toway.libraryservice.repository.StockRepository;
import xyz.toway.shared.exception.WrongParamsException;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final LibraryRepository libraryRepository;
    private final LibraryStockRepository libraryStockRepository;
    private final BookServiceProxy bookServiceProxy;

    public StockService(@Autowired StockRepository stockRepository, @Autowired LibraryRepository libraryRepository, @Autowired LibraryStockRepository libraryStockRepository, @Autowired BookServiceProxy bookServiceProxy) {
        this.stockRepository = stockRepository;
        this.libraryRepository = libraryRepository;
        this.libraryStockRepository = libraryStockRepository;
        this.bookServiceProxy = bookServiceProxy;
    }

    public List<StockEntity> getAll() {
        return stockRepository.findAll();
    }

    public StockEntity save(StockModel model) {
        LibraryEntity library = libraryRepository.findById(model.libraryId())
                .orElseThrow(() -> new RuntimeException("No library with id=" + model.libraryId()));

        //check book
        checkBookExists(model.bookId());

        StockEntity entity = new StockEntity(model.bookId(), model.quantity(), model.price());
        entity.setLibrary(library);

        return stockRepository.save(entity);
    }

    public StockEntity update(StockModel item, Long id) {
        StockEntity stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No stock item with id=" + id));
        LibraryEntity libraryEntity = libraryRepository.findById(item.libraryId())
                .orElseThrow(() -> new RuntimeException("No library with id=" + item.libraryId()));

        //check book
        checkBookExists(item.bookId());

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

    private void checkBookExists(Long id) {
        boolean bookExists = bookServiceProxy.checkBookExists(id);
        if (!bookExists) {
            throw new RuntimeException("No book with id=" + id);
        }
    }

    public boolean checkBeforeSale(Long libraryId, Long bookId, Integer quantity) {
        var stockOptional = libraryStockRepository.findFirstByIdAndBookId(libraryId, bookId);
        return stockOptional.isPresent() && stockOptional.get().getQuantity() >= quantity;
    }
}
