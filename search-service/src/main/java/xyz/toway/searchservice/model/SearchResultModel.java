package xyz.toway.searchservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import xyz.toway.shared.model.SharedBookModel;
import xyz.toway.shared.model.SharedLibraryModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchResultModel(SharedLibraryModel library, SharedBookModel book, String instanceUUID) {
}
