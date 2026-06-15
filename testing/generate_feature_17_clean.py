import json

# Define the centers of attention
centers = [
    "Centro Médico Integral",
    "Centro de Salud Dr. Juan Perez",
    "Trelew Salud",
    "Centro Médico Esperanza",
    "Clinica Rawson",
    "Centro de Rehabilitación",
    "Instituto Médico Patagonia",
    "Centro Odontológico Rawson",
    "Centro Médico del Este"
]

# Existing consultorios counts per center in database after feature 5 runs
existing_consultorios = {
    "Centro Médico Integral": 5,        # Norte, Sur, Este, Oeste, Central (101-105)
    "Centro de Salud Dr. Juan Perez": 5, # Cardiología, Dermatología, Neurología, Odontología, Ginecología (201-205)
    "Trelew Salud": 15,                 # Consultorio 1 to 15 (301-315)
    "Centro Médico Esperanza": 6,       # Consultorio 1 to 6 (501-506)
    "Clinica Rawson": 7,                # Consultorio 1 to 7 (601-607)
    "Centro de Rehabilitación": 5,      # Consultorio 1 to 5 (701-705)
    "Instituto Médico Patagonia": 5,    # Consultorio 1 to 5 (801-805)
    "Centro Odontológico Rawson": 5,    # Consultorio 1 to 5 (901-905)
    "Centro Médico del Este": 7         # Consultorio 1 to 7 (1001-1007)
}

# The starting number of new consultorios to create
start_numbers = {
    "Centro Médico Integral": 106,
    "Centro de Salud Dr. Juan Perez": 206,
    "Trelew Salud": 316,
    "Centro Médico Esperanza": 507,
    "Clinica Rawson": 608,
    "Centro de Rehabilitación": 706,
    "Instituto Médico Patagonia": 806,
    "Centro Odontológico Rawson": 906,
    "Centro Médico del Este": 1008
}

# Generate consultorios scenarios (exactly 20 per center total)
consultorios_scenarios = []
for center in centers:
    exist = existing_consultorios[center]
    needed = 20 - exist
    start_num = start_numbers[center]
    for i in range(needed):
        num = start_num + i
        c_index = exist + 1 + i
        name = f"Consultorio {c_index}"
        
        scenario = f"""Escenario: Creación de {name} para {center}
Dado que existe un centro de atención llamado "{center}"
Cuando se registra un consultorio con el número {num} y el nombre "{name}"
Entonces el sistema responde con 200 y "Consultorio creado exitosamente"
"""
        consultorios_scenarios.append(scenario)

# Define doctors
doctors = [
    {"nombre": "Juan", "apellido": "Pérez", "dni": 35123456, "matricula": "MP-JP01", "especialidad": "Cardiología"},
    {"nombre": "Roberto", "apellido": "Fernández", "dni": 35123460, "matricula": "MP-RF01", "especialidad": "Oftalmología"},
    {"nombre": "Ana", "apellido": "López", "dni": 35123461, "matricula": "MP-AL01", "especialidad": "Pediatría"},
    {"nombre": "Carlos", "apellido": "Gómez", "dni": 35123462, "matricula": "MP-CG01", "especialidad": "Odontología"},
    {"nombre": "Mario", "apellido": "Rodríguez", "dni": 35123463, "matricula": "MP-MR01", "especialidad": "Gastroenterología"},
    {"nombre": "Laura", "apellido": "Sánchez", "dni": 35123464, "matricula": "MP-LS01", "especialidad": "Pediatría"},
    {"nombre": "Valentina", "apellido": "Rivas", "dni": 44001001, "matricula": "VA-RV01", "especialidad": "Oftalmología"},
    {"nombre": "Germán", "apellido": "Ríos", "dni": 44001002, "matricula": "VA-GR01", "especialidad": "Oftalmología"},
    {"nombre": "Ezequiel", "apellido": "Molina", "dni": 44001003, "matricula": "VA-EM01", "especialidad": "Gastroenterología"},
    {"nombre": "Paola", "apellido": "Delgado", "dni": 44001004, "matricula": "VA-PD01", "especialidad": "Reumatología"},
    {"nombre": "Rodrigo", "apellido": "Acosta", "dni": 44001005, "matricula": "VA-RA01", "especialidad": "Urología"},
    {"nombre": "Sebastián", "apellido": "Paredes", "dni": 44001006, "matricula": "VA-SP01", "especialidad": "Dermatología"},
    {"nombre": "Camila", "apellido": "Luna", "dni": 44001007, "matricula": "VA-CL01", "especialidad": "Neurología"},
    {"nombre": "Mariana", "apellido": "Suárez", "dni": 44001008, "matricula": "VA-MS01", "especialidad": "Odontología"},
    {"nombre": "Agustina", "apellido": "Reyes", "dni": 44001009, "matricula": "VA-AR01", "especialidad": "Ortopedia y Traumatología"},
    {"nombre": "Antonella", "apellido": "Figueroa", "dni": 44001011, "matricula": "VA-AF01", "especialidad": "Endocrinología"},
    {"nombre": "Marcos", "apellido": "Villalba", "dni": 44001012, "matricula": "VA-MV01", "especialidad": "Gastroenterología"},
    {"nombre": "Matías", "apellido": "Salinas", "dni": 44001013, "matricula": "VA-MS02", "especialidad": "Ginecología"},
    {"nombre": "Santiago", "apellido": "Silva", "dni": 44001014, "matricula": "VA-SS01", "especialidad": "Reumatología"},
    {"nombre": "Micaela", "apellido": "Cabrera", "dni": 44001015, "matricula": "VA-MC01", "especialidad": "Cardiología"},
    {"nombre": "Luciana", "apellido": "Benítez", "dni": 44001016, "matricula": "VA-LB01", "especialidad": "Pediatría"},
    {"nombre": "Natalia", "apellido": "Espinosa", "dni": 44001017, "matricula": "VA-NE01", "especialidad": "Geriatría"},
    {"nombre": "Florencia", "apellido": "Méndez", "dni": 44001018, "matricula": "VA-FM01", "especialidad": "Cardiología"},
    {"nombre": "Nicolás", "apellido": "Herrera", "dni": 44001019, "matricula": "VA-NH01", "especialidad": "Cardiología"},
    {"nombre": "Ignacio", "apellido": "Fuentes", "dni": 44001020, "matricula": "VA-IF01", "especialidad": "Cardiología"},
    {"nombre": "Leandro", "apellido": "Peralta", "dni": 44001021, "matricula": "VA-LP01", "especialidad": "Infectología"},
    {"nombre": "Julián", "apellido": "Domínguez", "dni": 44001022, "matricula": "VA-JD01", "especialidad": "Hematología"},
    {"nombre": "Camila", "apellido": "Vega", "dni": 44001023, "matricula": "VA-CV01", "especialidad": "Odontología"},
    {"nombre": "Diego", "apellido": "Navarro", "dni": 44001024, "matricula": "VA-DN01", "especialidad": "Dermatología"},
    {"nombre": "Rocío", "apellido": "Castro", "dni": 44001025, "matricula": "VA-RC01", "especialidad": "Dermatología"},
    {"nombre": "Lucía", "apellido": "Giménez", "dni": 44001026, "matricula": "VA-LG01", "especialidad": "Oncología"},
    {"nombre": "Facundo", "apellido": "Cáceres", "dni": 44001027, "matricula": "VA-FC01", "especialidad": "Oncología"},
    {"nombre": "Tomás", "apellido": "Arias", "dni": 44001028, "matricula": "VA-TA01", "especialidad": "Gastroenterología"},
    {"nombre": "Martín", "apellido": "Rojas", "dni": 44001029, "matricula": "VA-MR01", "especialidad": "Gastroenterología"},
    {"nombre": "Sofía", "apellido": "Montes", "dni": 44001030, "matricula": "VA-SM01", "especialidad": "Pediatría"},
    {"nombre": "Martina", "apellido": "Paz", "dni": 44001031, "matricula": "VA-MP01", "especialidad": "Pediatría"}
]

# Distribute doctors among centers (4 per center)
trelew_names = ["Ana López", "Mario Rodríguez", "Roberto Fernández", "Juan Pérez"]
trelew_docs = [d for d in doctors if f"{d['nombre']} {d['apellido']}" in trelew_names]
other_docs = [d for d in doctors if f"{d['nombre']} {d['apellido']}" not in trelew_names]

doc_distribution = {}
doc_distribution["Trelew Salud"] = trelew_docs

other_centers = [c for c in centers if c != "Trelew Salud"]
for idx, center in enumerate(other_centers):
    doc_distribution[center] = other_docs[idx*4 : (idx+1)*4]

# Assign designated consultorio to each doctor for their agendas
# Use the FIRST 4 newly created (globally unique) consultorios for each center to avoid search clashes
doctor_consultorio = {}
for center in centers:
    exist = existing_consultorios[center]
    docs_in_center = doc_distribution[center]
    for i, doc in enumerate(docs_in_center):
        fullname = f"{doc['nombre']} {doc['apellido']}"
        start_idx = exist + 1 + i
        doctor_consultorio[fullname] = f"Consultorio {start_idx}"

# Pre-existing associations from 09asociar-especialidad-centro.feature to avoid 409 errors
pre_associated = {
    ("Cardiología", "Trelew Salud"),
    ("Oftalmología", "Trelew Salud"),
    ("Odontología", "Trelew Salud"),
    ("Dermatología", "Trelew Salud"),
    ("Ginecología", "Trelew Salud"),
    ("Ortopedia y Traumatología", "Trelew Salud"),
    ("Urología", "Trelew Salud"),
    ("Geriatría", "Trelew Salud"),
    ("Infectología", "Trelew Salud"),
    ("Odontología", "Centro Médico Integral"),
    ("Oftalmología", "Centro Médico Integral"),
    ("Pediatría", "Centro Médico Integral"),
    ("Urología", "Centro de Salud Dr. Juan Perez"),
    ("Odontología", "Centro Médico Esperanza"),
    ("Ortopedia y Traumatología", "Centro Médico Esperanza"),
    ("Ginecología", "Clinica Rawson"),
    ("Cardiología", "Clinica Rawson"),
    ("Pediatría", "Centro de Rehabilitación"),
    ("Cardiología", "Centro de Rehabilitación"),
    ("Cardiología", "Instituto Médico Patagonia"),
    ("Infectología", "Instituto Médico Patagonia"),
    ("Hematología", "Instituto Médico Patagonia"),
    ("Odontología", "Instituto Médico Patagonia"),
    ("Dermatología", "Centro Odontológico Rawson"),
}

# Generate specialty associations scenarios (only if not pre-associated)
specialty_scenarios = []
associated_pairs = set()

# First, associate all specialties of all doctors to Trelew Salud
for doc in doctors:
    spec = doc["especialidad"]
    pair = (spec, "Trelew Salud")
    if pair not in pre_associated and pair not in associated_pairs:
        associated_pairs.add(pair)
        scenario = f"""Escenario: Asociar especialidad {spec} a Trelew Salud
Cuando el administrador asocia la especialidad "{spec}" al centro de atención "Trelew Salud"
Entonces el sistema responde con 200 y "Asociación de especialidad en centro realizada correctamente"
"""
        specialty_scenarios.append(scenario)

# Then, associate specialties for other centers
for center in other_centers:
    for doc in doc_distribution[center]:
        spec = doc["especialidad"]
        pair = (spec, center)
        if pair not in pre_associated and pair not in associated_pairs:
            associated_pairs.add(pair)
            scenario = f"""Escenario: Asociar especialidad {spec} a {center}
Cuando el administrador asocia la especialidad "{spec}" al centro de atención "{center}"
Entonces el sistema responde con 200 y "Asociación de especialidad en centro realizada correctamente"
"""
            specialty_scenarios.append(scenario)

# Generate doctor association examples table
doctor_association_lines = []
for doc in doctors:
    # Always associate with Trelew Salud
    line_ts = f"| {doc['nombre']} | {doc['apellido']} | {doc['dni']} | {doc['matricula']} | {doc['especialidad']} | Trelew Salud |"
    doctor_association_lines.append(line_ts)
    
    # If their distributed center is not Trelew Salud, also associate with their distributed center
    assigned_center = None
    for center, docs in doc_distribution.items():
        if doc in docs:
            assigned_center = center
            break
    if assigned_center and assigned_center != "Trelew Salud":
        line_other = f"| {doc['nombre']} | {doc['apellido']} | {doc['dni']} | {doc['matricula']} | {doc['especialidad']} | {assigned_center} |"
        doctor_association_lines.append(line_other)

# Generate agendas
agenda_scenarios = []

# Pre-configure the 3 agendas expected by 18-agenda.feature to be already in DB
pre_configured_agendas = """Escenario: Agenda TS Pre-configuracion Ana Lopez
Dado que el administrador configura la agenda del "Consultorio 2"
Y define el horario de atención de "09:00" a "13:00" de lunes a viernes
Y asigna al Dr. "Ana López" con especialidad "Pediatría"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200

Escenario: Agenda TS Pre-configuracion Mario Rodriguez
Dado que el administrador configura la agenda del "Consultorio 3"
Y define el horario de atención de "08:00" a "12:00" de lunes a viernes
Y asigna al Dr. "Mario Rodríguez" con especialidad "Gastroenterología"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200

Escenario: Agenda TS Pre-configuracion Roberto Fernandez
Dado que el administrador configura la agenda del "Consultorio 6"
Y define el horario de atención de "08:00" a "12:00" de lunes a viernes
Y asigna al Dr. "Roberto Fernández" con especialidad "Oftalmología"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200
"""
agenda_scenarios.append(pre_configured_agendas)

# Exclude the 4 doctors tested in 18-agenda.feature from automatic/generated agendas in 17
# to avoid 409 conflicts during the 18-agenda.feature run.
excluded_from_agendas = {"Juan Pérez", "Ana López", "Mario Rodríguez", "Roberto Fernández"}

# Standard time slots
slots = [
    ("08:00", "10:00"),
    ("10:00", "12:00"),
    ("12:00", "14:00"),
    ("14:00", "16:00"),
    ("16:00", "18:00")
]

agenda_counter = 1

# We have 32 other doctors. We need exactly 147 generated agendas + 3 pre-configured = 150.
# 19 doctors * 5 agendas + 13 doctors * 4 agendas = 147 agendas.
other_doctors_list = [d for d in doctors if f"{d['nombre']} {d['apellido']}" not in excluded_from_agendas]

for idx, doc in enumerate(other_doctors_list):
    fullname = f"{doc['nombre']} {doc['apellido']}"
    consultorio = doctor_consultorio[fullname]
    
    num_agendas = 5 if idx < 19 else 4
    doc_slots = slots[:num_agendas]
        
    for start, end in doc_slots:
        scenario = f"""Escenario: Agenda TS {agenda_counter}
Dado que el administrador configura la agenda del "{consultorio}"
Y define el horario de atención de "{start}" a "{end}" de lunes a viernes
Y asigna al Dr. "{fullname}" con especialidad "{doc['especialidad']}"
Cuando guarda la configuración
Entonces el sistema confirma la creación de la agenda con código 200
"""
        agenda_scenarios.append(scenario)
        agenda_counter += 1

# Write everything to the feature file
feature_content = f"""# language: es

Característica: Preparación de datos para Agenda

"""

# Add consultorio creations
feature_content += "\n".join(consultorios_scenarios) + "\n\n"

# Add specialty associations
feature_content += "\n".join(specialty_scenarios) + "\n\n"

# Add doctor associations Scenario Outline
feature_content += """Esquema del escenario: Añadir médicos faltantes para las pruebas de esquemas de turnos
Dado que existe un sistema de gestión de centros de atención
Cuando el administrador asocia el médico con "<nombre>", "<apellido>", <dni>, "<matrícula>" y "<especialidad>" al centro de atención "<centro>"
Entonces el sistema responde con 200 y "Asociación de médico en centro realizada correctamente"

Ejemplos:
| nombre | apellido | dni | matrícula | especialidad | centro |
"""
feature_content += "\n".join(doctor_association_lines) + "\n\n"

# Add agendas
feature_content += "\n".join(agenda_scenarios) + "\n"

# Write to 17añadir_medico.feature
with open("/home/nerea/Escritorio/Laboratorio/turnero-web/testing/features/17añadir_medico.feature", "w", encoding="utf-8") as f:
    f.write(feature_content)

print("Feature file generated successfully!")
print(f"Total agendas generated: {agenda_counter - 1} + 3 pre-configured = {agenda_counter + 2}")
