
package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        // id'li ogrenci var mi kontrolu :
        Student student = findStudent(id);

    }


}
