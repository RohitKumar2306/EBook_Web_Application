package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "BorrowedBook")
@NamedQuery(name="BorrowedBook.findAll",query="select b from BorrowedBook b")
public class BorrowedBook extends AbstractClass {

    @Column(name = "borrow_date", nullable = false)
    @NotNull(message = "Borrow date is mandatory")
    private LocalDateTime borrowDate;

    @Column(name = "return_date", nullable = false)
    @NotNull(message = "Return date is mandatory")
   // @FutureOrPresent(message = "Return date must be today or in the future")
    private LocalDateTime expectedReturnDate;

    @Column(name = "returned_on")
    private LocalDateTime returnedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull(message = "Borrow status is mandatory")
    private BorrowStatus status;

    @Column(name = "total_cost", nullable = false)
    @NotNull(message = "Cost is mandatory")
    private Double bookBorrowCost;
    /**
     * Entity RelationShips
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @NotNull(message = "User Id is mandatory")
    @JsonBackReference("user-borrowedBooks")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    @NotNull(message = "Book Id is mandatory")
    @JsonBackReference("book-borrowedBooks")
    private Book book;

    @OneToOne
    @JoinColumn(name = "fineId")
    @JsonManagedReference("borrowedBook-fine")
    private Fine fine;

    @OneToOne
    private Reservation reservation;

    public BorrowedBook() {
    }

    public BorrowedBook(LocalDateTime borrowDate, LocalDateTime expectedReturnDate, LocalDateTime returnedOn, BorrowStatus status, Double bookBorrowCost) {
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.returnedOn = returnedOn;
        this.status = status;
        this.bookBorrowCost = bookBorrowCost;
    }

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "borrowDate=" + borrowDate +
                ", returnDate=" + expectedReturnDate +
                ", returnedOn=" + returnedOn +
                ", status=" + status +
//                ", user=" + user +
//                ", book=" + book +
//                ", fine=" + fine +
                '}';
    }

    /**
     * Getters and Setters
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Fine getFine() {
        return fine;
    }

    public void setFine(Fine fine) {
        this.fine = fine;
    }

    public @NotNull(message = "Borrow date is mandatory") LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public  LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(  LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDateTime getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDateTime returnedOn) {
        this.returnedOn = returnedOn;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public @NotNull(message = "Cost is mandatory") Double getBookBorrowCost() {
        return bookBorrowCost;
    }

    public void setBookBorrowCost(@NotNull(message = "Cost is mandatory") Double bookBorrowCost) {
        this.bookBorrowCost = bookBorrowCost;
    }
}
