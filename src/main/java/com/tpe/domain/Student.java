package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Student { // student

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "first name can not be null")
    @NotBlank(message = "first name can not be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} chars")
    @Column(nullable = false, length = 25) //
    private String name;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column
    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Provide valid email")
    private String email; //dfsfsfsfd   zxczxz@dfsfsd.com   /// email : dagjgajdga

    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();

}