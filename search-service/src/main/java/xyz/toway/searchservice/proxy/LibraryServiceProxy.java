package xyz.toway.searchservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.toway.shared.model.SharedLibraryModel;
import xyz.toway.shared.model.SharedLibraryStockModel;

import java.util.List;

@FeignClient(name = "library-service")
public interface LibraryServiceProxy {

    @GetMapping("/libraries/search-by-book-ids")
    List<SharedLibraryModel> searchByBookIds(@RequestParam("id") List<Long> ids);

    @GetMapping("/libraries/search-stock-by-ids")
    List<SharedLibraryStockModel> searchStockByBookIds(@RequestParam("id") List<Long> ids);
}
