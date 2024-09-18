package xyz.toway.bookservice.model;

public record BookModel(Integer id, Integer authorId, String title, Integer edition, String[] tags) {
}
