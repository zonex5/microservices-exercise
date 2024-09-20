package xyz.toway.shared.model;

public record SharedBookModel(Long id, String title, Integer edition, String[] tags, SharedAuthorModel author) {
}
