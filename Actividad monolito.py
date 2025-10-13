from flask import Flask, request, jsonify
import time

class Curso:
    def __init__(self, id, nombre):
        self.id = id
        self.nombre = nombre

class Estudiante:
    def __init__(self, id, nombre):
        self.id = id
        self.nombre = nombre

cursos = []
estudiantes = []

class CursoRepository:
    def crear(self, curso):
        cursos.append(curso)
        return curso
    def listar(self):
        return cursos

class EstudianteRepository:
    def crear(self, estudiante):
        estudiantes.append(estudiante)
        return estudiante
    def listar(self):
        return estudiantes

curso_repository = CursoRepository()
estudiante_repository = EstudianteRepository()

class CursoService:
    def crear_curso(self, nombre):
        curso = Curso(int(time.time() * 1000), nombre)
        return curso_repository.crear(curso)
    def listar_cursos(self):
        return curso_repository.listar()

class EstudianteService:
    def crear_estudiante(self, nombre):
        estudiante = Estudiante(int(time.time() * 1000), nombre)
        return estudiante_repository.crear(estudiante)
    def listar_estudiantes(self):
        return estudiante_repository.listar()

curso_service = CursoService()
estudiante_service = EstudianteService()

app = Flask(__name__)

@app.route("/cursos", methods=["POST"])
def crear_curso():
    data = request.get_json()
    nombre = data.get("nombre")
    nuevo_curso = curso_service.crear_curso(nombre)
    return jsonify(vars(nuevo_curso)), 201

@app.route("/cursos", methods=["GET"])
def listar_cursos():
    cursos_list = curso_service.listar_cursos()
    return jsonify([vars(c) for c in cursos_list])

@app.route("/estudiantes", methods=["POST"])
def crear_estudiante():
    data = request.get_json()
    nombre = data.get("nombre")
    nuevo_estudiante = estudiante_service.crear_estudiante(nombre)
    return jsonify(vars(nuevo_estudiante)), 201

@app.route("/estudiantes", methods=["GET"])
def listar_estudiantes():
    estudiantes_list = estudiante_service.listar_estudiantes()
    return jsonify([vars(e) for e in estudiantes_list])

if __name__ == "__main__":
    app.run(port=3000, debug=True) 

