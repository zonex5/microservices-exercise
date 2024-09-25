package xyz.toway.sales.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.sales.model.SaleModel;
import xyz.toway.sales.service.SaleService;
import xyz.toway.shared.exception.WrongParamsException;

@Log4j2
@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(@Autowired SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    private ResponseEntity<?> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getSale(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(saleService.getSaleById(id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    private ResponseEntity<?> addSale(@Valid @RequestBody SaleModel sale) {
        try {
            return ResponseEntity.ok(saleService.createSale(sale));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AmqpException e) {
            log.error("RabbitMQ exception:", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.ok().build();
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
