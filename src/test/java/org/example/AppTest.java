package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.*;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();
    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void tc_1_AddStudent() {
        Iterable<Student> students = service.findAllStudents();
        int result = service.saveStudent("10", "alex", 932);
        assertEquals(1, result);
    }

    @Test
    public void tc_3_AddInvalidStudent() {
        Iterable<Student> students = service.findAllStudents();
        int result = 0;
        try {
            result = service.saveStudent("10", "alex", 8);

            assertEquals(0, result);
        }catch (ValidationException e) {
            assertEquals(null, result);
        }
    }

    @Test
    public void tc_2_AddExistingStudent() {
        Iterable<Student> students = service.findAllStudents();
        int result = service.saveStudent("10", "alex", 932);

        assertEquals(1, result);
    }

    @Test
    public void tc_4_InvalidID() {
        Iterable<Student> students = service.findAllStudents();
        int result = 0;
        try {
            result = service.saveStudent("", "alex", 800);

            assertEquals(0, result);
        }catch (ValidationException e) {
            assertEquals(null, result);
        }
    }

    @Test
    public void tc_5_InvalidName() {
        Iterable<Student> students = service.findAllStudents();
        int result = 0;
        try {
            result = service.saveStudent("10", "", 800);

            assertEquals(0, result);
        }catch (ValidationException e) {
            assertEquals(null, result);
        }
    }

}
