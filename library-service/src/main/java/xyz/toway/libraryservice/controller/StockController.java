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
import xyz.toway.shared.exception.WrongParamsException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/stock")
public class StockController {

    private final String GENERAL_ERROR = "Something went wrong.";

    private final StockService stockService;

    public StockController(@Autowired StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    private ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(stockService.getAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(stockService.getById(id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @PostMapping
    private ResponseEntity<?> addStockItem(@Valid @RequestBody StockModel item) {
        try {
            return ResponseEntity.ok(stockService.save(item));
        } catch (DataIntegrityViolationException | WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateStockItem(@Valid @RequestBody StockModel item, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(stockService.update(item, id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteStockItem(@PathVariable Long id) {
        try {
            stockService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/check")
    private ResponseEntity<?> checkBeforeSale(@RequestParam("libraryId") Long libraryId, @RequestParam("bookId") Long bookId, @RequestParam("quantity") Integer quantity) {
        try {
            return ResponseEntity.ok(stockService.checkBeforeSale(libraryId, bookId, quantity));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
