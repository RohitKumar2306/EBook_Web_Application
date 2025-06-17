package com.ebook.domain.JPATest;

import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookJPATest extends AbstractJPATest{

    private Publisher publisher;

    public static String generateRandomPhoneNumber() {
        long number = (long)(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(number);
    }

    @BeforeEach
    public void setUpPublisher() {
        String uniqueName = "publisher_" + UUID.randomUUID();
        String randomPhone = generateRandomPhoneNumber();
        publisher = new Publisher(uniqueName, "address", uniqueName + "@gmail.com", randomPhone);
        persistEntity(publisher);
    }

    @AfterEach
    public void cleanUpPublisher() {
        tx.begin();

        Publisher managedPublisher = findEntity(Publisher.class, publisher.getId());
        if (managedPublisher != null) {
            List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.publisher.id = :publisherId", Book.class)
                    .setParameter("publisherId", managedPublisher.getId())
                    .getResultList();

            for (Book book : books) {
                em.remove(book);
            }

            em.remove(managedPublisher);
        }
        tx.commit();
        em.close();
    }

    @Test
    public void createTest() {
        Book book = new Book(1999, 2, 100, "english", "harry2", "2000000000002");
        book.setPublisher(publisher);
        persistEntity(book);
        Book readFromDb = findEntity(Book.class, book.getId());
        assertNotNull(readFromDb);
        assertEquals(book.getId(), readFromDb.getId());
    }

    @Test
    public void readTest() {
        Book book = new Book(1990, 20, 200, "english", "HarryPotter5", "3006080881000");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM Book b WHERE b.title = 'HarryPotter5'", Book.class).getSingleResult();
        assertNotNull(findBook);
        assertEquals("HarryPotter5", findBook.getTitle());
    }

    @Test
    public void updateTest() {
        Book book = new Book(1970, 300, 1200, "english", "LordOfTheRing4", "5040000000100");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = em.createQuery("SELECT b FROM Book b WHERE b.title = 'LordOfTheRing4'", Book.class).getSingleResult();
        tx.begin();
        findBook.setTitle("Lord of The Rings4");
        em.merge(findBook);
        tx.commit();
        Book updatedBook = findEntity(Book.class, book.getId());
        assertEquals("Lord of The Rings4", updatedBook.getTitle());
    }

    /*
    @Test
    public void deleteTest() {
        Book book = new Book(2004, 50, 5000, "english", "PeterEngland4", "7040002000000");
        book.setPublisher(publisher);
        persistEntity(book);
        Book findBook = findEntity(Book.class, book.getId());
        assertNotNull(findBook);
        removeEntity(findBook);
        Book deletedBook = findEntity(Book.class, book.getId());
        assertNull(deletedBook);
    }

     */
}
