package xyz.toway.sales.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.sales.entity.SaleEntity;
import xyz.toway.sales.model.SaleModel;
import xyz.toway.sales.proxy.LibraryServiceProxy;
import xyz.toway.sales.repository.SaleRepository;
import xyz.toway.shared.exception.WrongParamsException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final LibraryServiceProxy libraryServiceProxy;

    public SaleService(@Autowired SaleRepository saleRepository, @Autowired LibraryServiceProxy libraryServiceProxy) {
        this.saleRepository = saleRepository;
        this.libraryServiceProxy = libraryServiceProxy;
    }

    public List<SaleModel> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(this::createSaleModel)
                .toList();
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    public SaleModel createSale(@Valid SaleModel sale) {
        //check library id, book id, quantity from stock
        if (libraryServiceProxy.checkBeforeSale(sale.libraryId(), sale.bookId(), sale.quantity())) {
            SaleEntity entity = createSaleEntity(sale);
            return createSaleModel(saleRepository.save(entity));
        } else {
            throw new WrongParamsException("Invalid parameters or insufficient quantity of books.");
        }
    }

    public SaleModel updateSale(SaleModel sale, Long id) { //todo remove
        SaleEntity existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("The sale with id=" + id + " does not exist."));

        //todo check library id, book id, quantity from stock

        existingSale.setSaleDate(sale.saleDate());
        existingSale.setQuantity(sale.quantity());
        existingSale.setBookId(sale.bookId());
        existingSale.setLibraryId(sale.libraryId());
        return createSaleModel(saleRepository.save(existingSale));
    }

    public SaleModel getSaleById(Long id) {
        SaleEntity sale = saleRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("The sale with id=" + id + " does not exist."));
        return createSaleModel(sale);
    }

    private SaleModel createSaleModel(SaleEntity entity) {
        return new SaleModel(entity.getId(), entity.getLibraryId(), entity.getBookId(), entity.getQuantity(), entity.getSaleDate());
    }

    private SaleEntity createSaleEntity(SaleModel model) {
        return new SaleEntity(
                model.id(),
                model.libraryId(),
                model.bookId(),
                model.quantity(),
                Objects.requireNonNullElse(model.saleDate(), LocalDateTime.now())
        );
    }
}
