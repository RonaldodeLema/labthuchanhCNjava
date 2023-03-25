package tdtu.edu.ex04.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import tdtu.edu.ex04.models.Student;
import tdtu.edu.ex04.repository.StudentRepository;

@Service
public interface StudentService extends StudentRepository {
    public Iterable<Student> findAllByAgeGreaterThan(Integer x);
    public Iterable<Student> findAllByIeltsScoreEquals(Double x);
    public Iterable<Student> findAllByNameContaining(String xxx);
}
