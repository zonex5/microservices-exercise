package xyz.toway.searchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.searchservice.model.SearchResultModel;
import xyz.toway.shared.exception.WrongParamsException;
import xyz.toway.shared.model.SharedBookModel;
import xyz.toway.shared.model.SharedLibraryModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final static String SEARCH_Q = "q";
    private final static String SEARCH_BY = "by";

    private final ProxyService proxyService;

    public SearchService(@Autowired ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    public List<SearchResultModel> searchBooks(Map<String, String> params) {
        checkQueryParams(params);

        var booksList = proxyService.getBooksFromRemoteService(params.get(SEARCH_Q), params.get(SEARCH_BY));
        var ids = booksList.stream().map(SharedBookModel::id).toList();
        var stock = proxyService.getStockFromRemoteService(ids);
        var existingBooksMap = booksList.stream().collect(Collectors.toMap(SharedBookModel::id, book -> book));

        return stock.stream()
                .map(e -> new SearchResultModel(
                        new SharedLibraryModel(e.id(), e.name(), e.address()),
                        existingBooksMap.get(e.bookId()))
                )
                .toList();
    }

    public List<SharedLibraryModel> searchLibraries(Map<String, String> params) {
        checkQueryParams(params);

        var ids = proxyService.getBookIdsFromRemoteService(params.get(SEARCH_Q), params.get(SEARCH_BY));
        return proxyService.getLibrariesByBookIdsFromRemoteService(ids);
    }

    private static void checkQueryParams(Map<String, String> params) {
        if (params == null || !params.containsKey(SEARCH_Q) || !params.containsKey(SEARCH_BY)) {
            throw new WrongParamsException("The search parameters are incorrect. Missed required param 'q=' or 'by=' (tag, title, author)");
        }
    }
}
