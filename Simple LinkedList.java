// Student.java
public class Student {
    String firstName;
    String lastName;
    int idNumber;
    int semester;
    String program;
    Student next;  // Puntero al siguiente estudiante
    
    public Student(String firstName, String lastName, int idNumber, int semester, String program) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.semester = semester;
        this.program = program;
        this.next = null;
    }
}
// Class.java
public class Class {
    int id;
    String name;
    int credits;
    Student head;  // Puntero al primer estudiante (head de la lista enlazada)
    
    public Class(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.head = null;
    }

    // Validar si los datos del estudiante son correctos
    private void validateStudent(Student student) throws Exception {
        if (this.credits <= 0) {
            throw new Exception("La clase " + this.name + " no tiene créditos válidos.");
        }
        if (studentExists(student.idNumber)) {
            throw new Exception("El alumno con ID " + student.idNumber + " ya está inscrito.");
        }
    }

    // Verificar si un estudiante ya está registrado por idNumber
    private boolean studentExists(int idNumber) {
        Student current = this.head;
        while (current != null) {
            if (current.idNumber == idNumber) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Registrar un nuevo estudiante en la clase
    public void registerStudent(Student student) throws Exception {
        validateStudent(student);
        
        // Insertar al inicio o en la cabeza de la lista
        if (head == null || student.lastName.compareTo(head.lastName) < 0) {
            student.next = head;
            head = student;
        } else {
            Student current = head;
            while (current.next != null && current.next.lastName.compareTo(student.lastName) < 0) {
                current = current.next;
            }
            student.next = current.next;
            current.next = student;
        }
    }

    // Actualizar los datos de un estudiante
    public void updateStudent(int idNumber, String firstName, String lastName, int semester, String program) {
        Student current = head;
        while (current != null) {
            if (current.idNumber == idNumber) {
                if (firstName != null) current.firstName = firstName;
                if (lastName != null) current.lastName = lastName;
                if (semester > 0) current.semester = semester;
                if (program != null) current.program = program;
                System.out.println("Estudiante con ID " + idNumber + " actualizado.");
                return;
            }
            current = current.next;
        }
        System.out.println("Estudiante con ID " + idNumber + " no encontrado.");
    }

    // Eliminar un estudiante de la lista
    public void removeStudent(int idNumber) {
        Student current = head;
        Student prev = null;
        
        while (current != null) {
            if (current.idNumber == idNumber) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    head = current.next;
                }
                System.out.println("Estudiante con ID " + idNumber + " eliminado.");
                return;
            }
            prev = current;
            current = current.next;
        }
        System.out.println("Estudiante con ID " + idNumber + " no encontrado.");
    }

    // Consultar todos los estudiantes
    public void listStudents() {
        Student current = head;
        if (current == null) {
            System.out.println("No hay estudiantes inscritos.");
            return;
        }
        while (current != null) {
            System.out.println("ID: " + current.idNumber + ", Nombre: " + current.firstName + " " + current.lastName +
                               ", Semestre: " + current.semester + ", Programa: " + current.program);
            current = current.next;
        }
    }
}
// Main.java
public class Main {
    public static void main(String[] args) {
        // Crear una clase
        Class programmingClass = new Class(101, "Programación Avanzada", 3);
        
        // Crear estudiantes
        Student student1 = new Student("Juan", "Pérez", 1, 2, "Ingeniería de Sistemas");
        Student student2 = new Student("María", "Gómez", 2, 3, "Matemáticas");
        Student student3 = new Student("Carlos", "Martínez", 3, 1, "Ingeniería de Sistemas");
        
        try {
            // Registrar estudiantes
            programmingClass.registerStudent(student1);
            programmingClass.registerStudent(student2);
            programmingClass.registerStudent(student3);

            // Consultar estudiantes
            System.out.println("Lista de estudiantes inscritos:");
            programmingClass.listStudents();
            
            // Actualizar un estudiante
            programmingClass.updateStudent(2, null, null, 4, null);
            
            // Eliminar un estudiante
            programmingClass.removeStudent(1);

            // Consultar nuevamente
            System.out.println("\nLista de estudiantes después de la actualización y eliminación:");
            programmingClass.listStudents();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
