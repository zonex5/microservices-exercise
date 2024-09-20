package xyz.toway.searchservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.toway.shared.model.SharedLibraryModel;

import java.util.List;

@FeignClient(name = "library-service")
public interface LibraryServiceProxy {

    @GetMapping("/libraries/search-by-books")
    List<SharedLibraryModel> searchBooks(@RequestParam("id") List<Long> ids);
}
