package xyz.toway.searchservice.model;

import xyz.toway.shared.model.SharedBookModel;
import xyz.toway.shared.model.SharedLibraryModel;

public record SearchResultModel(SharedLibraryModel library, SharedBookModel bookModel) {
}
