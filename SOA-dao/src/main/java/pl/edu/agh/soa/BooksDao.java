package pl.edu.agh.soa;

import javax.ejb.Stateless;

@Stateless
public class BooksDao {
    public static BooksEntity bookToEntity(Book book) {
        BooksEntity bookEntity = new BooksEntity();
        bookEntity.setName(book.getName());
        return bookEntity;
    }

    public static Book entityToBook(BooksEntity booksEntity) {
        Book book = new Book();
        book.setName(booksEntity.getName());
        return book;
    }
}
