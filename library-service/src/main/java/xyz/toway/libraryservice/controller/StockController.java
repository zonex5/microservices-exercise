package xyz.toway.libraryservice.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.libraryservice.entity.StockEntity;
import xyz.toway.libraryservice.model.StockModel;
import xyz.toway.libraryservice.service.StockService;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(@Autowired StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        List<StockEntity> stock = stockService.getAll();
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    private ResponseEntity<?> addStockItem(@Valid @RequestBody StockModel item) {
        try {
            StockEntity stockEntity = stockService.save(item);
            return ResponseEntity.ok(stockEntity);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateStockItem(@Valid @RequestBody StockModel item, @PathVariable Long id) {
        try {
            StockEntity stockEntity = stockService.update(item, id);
            return ResponseEntity.ok(stockEntity);
        } catch (RuntimeException e) {
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteStockItem(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    private ResponseEntity<?> checkBeforeSale(@RequestParam("libraryId") Long libraryId, @RequestParam("bookId") Long bookId, @RequestParam("quantity") Integer quantity) {
        return ResponseEntity.ok(stockService.checkBeforeSale(libraryId, bookId, quantity));
    }
}
