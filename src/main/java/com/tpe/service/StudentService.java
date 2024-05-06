package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Not: getAll() ***************************************************
    public List<Student> getAll() {
        // !!! handle etmemiz gereken bir durum var mi ? YOK
        return studentRepository.findAll(); // SELECT * FROM student
    }

    // NOt: createStudent() ********************************************
    public void createStudent(Student student) {

        // !!! kontrol etmemiz gereken bir durum var mi ? Email unique mi kontrolu
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Email is already exist");
        }
        studentRepository.save(student);
    }

    // Not: getStudentById with RequestParam ****************************
    public Student findStudent(Long id) {

        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id: " + id));
    }

    // Not: Delete Student ************************************************
    public void deleteStudent(Long id) {

        Student student = findStudent(id);
        //studentRepository.delete(student);
        studentRepository.deleteById(id);
    }

    //Not: Update Student *************************************************
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //!!! id'li ogrenci var mi kontrolu :
        Student student = findStudent(id);
        // !!! email exist mi ? ve eger email degisecek ise DB de mevcutta olan emaillerden olmamasi gerekiyor
        boolean emailExist = studentRepository.existsByEmail(studentDTO.getEmail());

        if(emailExist && !studentDTO.getEmail().equals(student.getEmail())){
            throw new ConflictException("Email is already exist");
        }

        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        // !!! TRICK : asagidaki save ile beraber ogrencinin mevcut id bilgisi degisir mi ??
        // CEVAP : id degismez...
        studentRepository.save(student);
    }
    // Not: Pageable ****************************************************************************
    public Page<Student> getAllWithPage(Pageable pageable) {

        return studentRepository.findAll(pageable);
    }

    //Not: Get By LastName ***********************************************************************
    public List<Student> findStudent(String lastName){

        return studentRepository.findByLastName(lastName);
    }
    // NOT : Get ALL Student By Grade ( JPQL ) Java Persistance Query Language *******************
    public List<Student> findAllEqualsGrade(Integer grade) {
        return studentRepository.findAllEqualsGrade(grade);//JPQL
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id : " + id));
    }
}