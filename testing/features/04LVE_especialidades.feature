
            #Elimine la palabra "Caracteristica" porque sólo se puede utilizar una vez por feature.
            #También cambie una inconsistencia en el mensaje de "Especialidad creada exitosamente" y "Especialidad creada correctamentee y lo dejeé como "exitosamente"

            # language: es
            Característica: Gestión de Especialidades

            Esquema del escenario: Crear una especialidad exitosamente
            Cuando el administrador crea una especialidad con el nombre "<nombre>" y la descripción "<descripcion>"
            Entonces el sistema responde con <status_code> y "<status_text>"

            Ejemplos:
            | nombre                    | descripcion                                                                         | status_code | status_text                       |
            | Alergia e Inmunología     | Diagnóstico y tratamiento de enfermedades alérgicas e inmunológicas.                | 200         | Especialidad creada exitosamente |
            | Anatomía Patológica       | Estudio de tejidos y células para diagnosticar enfermedades.                        | 200         | Especialidad creada exitosamente |
            | Anestesiología            | Administración de anestesia para procedimientos quirúrgicos y control del dolor.    | 200         | Especialidad creada exitosamente |
            | Angiología                | Diagnóstico y tratamiento de enfermedades de los vasos sanguíneos y linfáticos.     | 200         | Especialidad creada exitosamente |
            | Cardiología               | Diagnóstico y tratamiento de enfermedades del corazón y el sistema circulatorio.    | 200         | Especialidad creada exitosamente |
            | Cirugía Cardiovascular    | Intervenciones quirúrgicas del corazón y grandes vasos sanguíneos.                  | 200         | Especialidad creada exitosamente |
            | Cirugía General           | Tratamiento quirúrgico de diversas patologías en órganos internos.                  | 200         | Especialidad creada exitosamente |
            | Cirugía Maxilofacial      | Cirugía de la cara, mandíbula y structures asociadas.                               | 200         | Especialidad creada exitosamente |
            | Cirugía Plástica          | Reconstrucción, reparación y embellecimiento de tejidos y estructuras del cuerpo.   | 200         | Especialidad creada exitosamente |
            | Cirugía Torácica          | Cirugía del tórax, pulmones y otras estructuras torácicas.                          | 200         | Especialidad creada exitosamente |
            | Cirugía Vascular          | Diagnóstico y tratamiento quirúrgico de enfermedades de los vasos sanguíneos.       | 200         | Especialidad creada exitosamente |
            | Clínica Médica            | Atención integral de enfermedades médicas en adultos.                               | 200         | Especialidad creada exitosamente |
            | Dermatología              | Diagnóstico y tratamiento de enfermedades de la piel, cabello y uñas.               | 200         | Especialidad creada exitosamente |
            | Diabetología              | Tratamiento y control de la diabetes y sus complicaciones.                          | 200         | Especialidad creada exitosamente |
            | Emergentología            | Atención médica de urgencias y emergencias.                                         | 200         | Especialidad creada exitosamente |
            | Endocrinología            | Diagnóstico y tratamiento de trastornos hormonales.                                 | 200         | Especialidad creada exitosamente |
            | Endoscopía Digestiva      | Exploración y tratamiento de enfermedades del tracto digestivo mediante endoscopía. | 200         | Especialidad creada exitosamente |
            | Fisiatría                 | Rehabilitación de personas con discapacidades físicas o motoras.                    | 200         | Especialidad creada exitosamente |
            | Gastroenterología         | Diagnóstico y tratamiento de enfermedades del sistema digestivo.                    | 200         | Especialidad creada exitosamente |
            | Genética Médica           | Estudio de enfermedades hereditarias y trastornos genéticos.                        | 200         | Especialidad creada exitosamente |
            | Geriatría                 | Atención médica integral del adulto mayor.                                          | 200         | Especialidad creada exitosamente |
            | Ginecología               | Diagnóstico y tratamiento de enfermedades del aparato reproductor femenino.         | 200         | Especialidad creada exitosamente |
            | Hematología               | Diagnóstico y tratamiento de enfermedades de la sangre y órganos hematopoyéticos.   | 200         | Especialidad creada exitosamente |
            | Hepatología               | Diagnóstico y tratamiento de enfermedades del hígado.                               | 200         | Especialidad creada exitosamente |
            | Infectología              | Estudio, diagnóstico y tratamiento de enfermedades infecciosas.                     | 200         | Especialidad creada exitosamente |
            | Medicina del Deporte      | Prevención y tratamiento de lesiones deportivas y mejora del rendimiento.           | 200         | Especialidad creada exitosamente |
            | Medicina del Trabajo      | Prevención y tratamiento de enfermedades laborales.                                 | 200         | Especialidad creada exitosamente |
            | Medicina Estética         | Procedimientos para mejorar la estética y apariencia física.                        | 200         | Especialidad creada exitosamente |
            | Medicina Familiar         | Atención integral de la salud en todas las etapas de la vida.                       | 200         | Especialidad creada exitosamente |
            | Medicina Forense          | Aplicación de la medicina en el ámbito legal y judicial.                            | 200         | Especialidad creada exitosamente |
            | Medicina General          | Atención primaria y general de la salud.                                            | 200         | Especialidad creada exitosamente |
            | Medicina Interna          | Diagnóstico y tratamiento de enfermedades en adultos sin necesidad de cirugía.      | 200         | Especialidad creada exitosamente |
            | Medicina Materno-Fetal    | Atención médica a embarazadas y fetos en riesgo.                                    | 200         | Especialidad creada exitosamente |
            | Nefrología                | Diagnóstico y tratamiento de enfermedades renales.                                  | 200         | Especialidad creada exitosamente |
            | Neonatología              | Atención médica de recién nacidos, especialmente prematuros o enfermos.             | 200         | Especialidad creada exitosamente |
            | Neumonología              | Diagnóstico y tratamiento de enfermedades pulmonares y respiratorias.               | 200         | Especialidad creada exitosamente |
            | Neurocirugía              | Cirugía del cerebro, médula espinal y nervios periféricos.                          | 200         | Especialidad creada exitosamente |
            | Neurología                | Diagnóstico y tratamiento de enfermedades del sistema nervioso.                     | 200         | Especialidad creada exitosamente |
            | Nutrición                 | Control de la alimentación y nutrición para la salud y prevención de enfermedades.  | 200         | Especialidad creada exitosamente |
            | Obstetricia               | Atención médica del embarazo, parto y postparto.                                    | 200         | Especialidad creada exitosamente |
            | Odontología               | Cuidado de la salud bucal y dental.                                                 | 200         | Especialidad creada exitosamente |
            | Oftalmología              | Diagnóstico y tratamiento de enfermedades de los ojos y visión.                     | 200         | Especialidad creada exitosamente |
            | Oncología                 | Diagnóstico y tratamiento del cáncer.                                               | 200         | Especialidad creada exitosamente |
            | Ortopedia y Traumatología | Diagnóstico y tratamiento de enfermedades del sistema musculoesquelético.           | 200         | Especialidad creada exitosamente |
            | Otorrinolaringología      | Diagnóstico y tratamiento de enfermedades del oído, nariz y garganta.               | 200         | Especialidad creada exitosamente |
            | Pediatría                 | Atención médica integral de niños y adolescentes.                                   | 200         | Especialidad creada exitosamente |
            | Psiquiatría               | Diagnóstico y tratamiento de trastornos mentales y emocionales.                     | 200         | Especialidad creada exitosamente |
            | Radiología                | Diagnóstico y tratamiento mediante técnicas de imagen médica.                       | 200         | Especialidad creada exitosamente |
            | Reumatología              | Diagnóstico y tratamiento de enfermedades reumáticas y autoinmunes.                 | 200         | Especialidad creada exitosamente |
            | Urología                  | Diagnóstico y tratamiento de enfermedades del aparato urinario y reproductor masc.  | 200         | Especialidad creada exitosamente |

            Esquema del escenario: Intentar crear una especialidad con nombre duplicado
            Cuando el administrador crea una especialidad con el nombre "<nombre>" y la descripción "<descripcion>"
            Entonces el sistema responde con <status_code> y "<status_text>"

            Ejemplos:
            | nombre            | descripcion                                  | status_code | status_text                                      |
            | Cardiología       | Especialidad que estudia el sistema cardíaco | 409         | Ya existe una especialidad con ese nombre        |
            | Ayurbeda          |                                              | 409         | La descripción de la especialidad es obligatoria |
            | Gastroenterología | Especialidad del sistema digestivo           | 409         | Ya existe una especialidad con ese nombre        |

            Escenario: Recuperar todas las especialidades registradas en el sistema
            Dado que existen 50 especialidades registradas en el sistema
            Cuando un usuario del sistema solicita la lista de especialidades
            Entonces el sistema responde con un JSON:
            """
            {
                "status_code": 200,
                "status_text": "especialidades recuperadas correctamente",
                "data": [
                    {
                        "nombre": "Alergia e Inmunología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades alérgicas e inmunológicas."
                    },
                    {
                        "nombre": "Anatomía Patológica",
                        "descripcion": "Estudio de tejidos y células para diagnosticar enfermedades."
                    },
                    {
                        "nombre": "Anestesiología",
                        "descripcion": "Administración de anestesia para procedimientos quirúrgicos y control del dolor."
                    },
                    {
                        "nombre": "Angiología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades de los vasos sanguíneos y linfáticos."
                    },
                    {
                        "nombre": "Cardiología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del corazón y el sistema circulatorio."
                    },
                    {
                        "nombre": "Cirugía Cardiovascular",
                        "descripcion": "Intervenciones quirúrgicas del corazón y grandes vasos sanguíneos."
                    },
                    {
                        "nombre": "Cirugía General",
                        "descripcion": "Tratamiento quirúrgico de diversas patologías en órganos internos."
                    },
                    {
                        "nombre": "Cirugía Maxilofacial",
                        "descripcion": "Cirugía de la cara, mandíbula y estructuras asociadas."
                    },
                    {
                        "nombre": "Cirugía Plástica",
                        "descripcion": "Reconstrucción, reparación y embellecimiento de tejidos y estructuras del cuerpo."
                    },
                    {
                        "nombre": "Cirugía Torácica",
                        "descripcion": "Cirugía del tórax, pulmones y otras estructuras torácicas."
                    },
                    {
                        "nombre": "Cirugía Vascular",
                        "descripcion": "Diagnóstico y tratamiento quirúrgico de enfermedades de los vasos sanguíneos."
                    },
                    {
                        "nombre": "Clínica Médica",
                        "descripcion": "Atención integral de enfermedades médicas en adultos."
                    },
                    {
                        "nombre": "Dermatología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades de la piel, cabello y uñas."
                    },
                    {
                        "nombre": "Diabetología",
                        "descripcion": "Tratamiento y control de la diabetes y sus complicaciones."
                    },
                    {
                        "nombre": "Emergentología",
                        "descripcion": "Atención médica de urgencias y emergencias."
                    },
                    {
                        "nombre": "Endocrinología",
                        "descripcion": "Diagnóstico y tratamiento de trastornos hormonales."
                    },
                    {
                        "nombre": "Endoscopía Digestiva",
                        "descripcion": "Exploración y tratamiento de enfermedades del tracto digestivo mediante endoscopía."
                    },
                    {
                        "nombre": "Fisiatría",
                        "descripcion": "Rehabilitación de personas con discapacidades físicas o motoras."
                    },
                    {
                        "nombre": "Gastroenterología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del sistema digestivo."
                    },
                    {
                        "nombre": "Genética Médica",
                        "descripcion": "Estudio de enfermedades hereditarias y trastornos genéticos."
                    },
                    {
                        "nombre": "Geriatría",
                        "descripcion": "Atención médica integral del adulto mayor."
                    },
                    {
                        "nombre": "Ginecología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del aparato reproductor femenino."
                    },
                    {
                        "nombre": "Hematología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades de la sangre y órganos hematopoyéticos."
                    },
                    {
                        "nombre": "Hepatología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del hígado."
                    },
                    {
                        "nombre": "Infectología",
                        "descripcion": "Estudio, diagnóstico y tratamiento de enfermedades infecciosas."
                    },
                    {
                        "nombre": "Medicina del Deporte",
                        "descripcion": "Prevención y tratamiento de lesiones deportivas y mejora del rendimiento."
                    },
                    {
                        "nombre": "Medicina del Trabajo",
                        "descripcion": "Prevención y tratamiento de enfermedades laborales."
                    },
                    {
                        "nombre": "Medicina Estética",
                        "descripcion": "Procedimientos para mejorar la estética y apariencia física."
                    },
                    {
                        "nombre": "Medicina Familiar",
                        "descripcion": "Atención integral de la salud en todas las etapas de la vida."
                    },
                    {
                        "nombre": "Medicina Forense",
                        "descripcion": "Aplicación de la medicina en el ámbito legal y judicial."
                    },
                    {
                        "nombre": "Medicina General",
                        "descripcion": "Atención primaria y general de la salud."
                    },
                    {
                        "nombre": "Medicina Interna",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades en adultos sin necesidad de cirugía."
                    },
                    {
                        "nombre": "Medicina Materno-Fetal",
                        "descripcion": "Atención médica a embarazadas y fetos en riesgo."
                    },
                    {
                        "nombre": "Nefrología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades renales."
                    },
                    {
                        "nombre": "Neonatología",
                        "descripcion": "Atención médica de recién nacidos, especialmente prematuros o enfermos."
                    },
                    {
                        "nombre": "Neumonología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades pulmonares y respiratorias."
                    },
                    {
                        "nombre": "Neurocirugía",
                        "descripcion": "Cirugía del cerebro, médula espinal y nervios periféricos."
                    },
                    {
                        "nombre": "Neurología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del sistema nervioso."
                    },
                    {
                        "nombre": "Nutrición",
                        "descripcion": "Control de la alimentación y nutrición para la salud y prevención de enfermedades."
                    },
                    {
                        "nombre": "Obstetricia",
                        "descripcion": "Atención médica del embarazo, parto y postparto."
                    },
                    {
                        "nombre": "Odontología",
                        "descripcion": "Cuidado de la salud bucal y dental."
                    },
                    {
                        "nombre": "Oftalmología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades de los ojos y visión."
                    },
                    {
                        "nombre": "Oncología",
                        "descripcion": "Diagnóstico y tratamiento del cáncer."
                    },
                    {
                        "nombre": "Ortopedia y Traumatología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del sistema musculoesquelético."
                    },
                    {
                        "nombre": "Otorrinolaringología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del oído, nariz y garganta."
                    },
                    {
                        "nombre": "Pediatría",
                        "descripcion": "Atención médica integral de niños y adolescentes."
                    },
                    {
                        "nombre": "Psiquiatría",
                        "descripcion": "Diagnóstico y tratamiento de trastornos mentales y emocionales."
                    },
                    {
                        "nombre": "Radiología",
                        "descripcion": "Diagnóstico y tratamiento mediante técnicas de imagen médica."
                    },
                    {
                        "nombre": "Reumatología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades reumáticas y autoinmunes."
                    },
                    {
                        "nombre": "Urología",
                        "descripcion": "Diagnóstico y tratamiento de enfermedades del aparato urinario y reproductor masculino."
                    }
                ]
            }
            """

            Esquema del escenario: Modificar una especialidad exitosamente
            Dado que la especialidad "<nombre_original>" existe en el sistema con la descripción "<descripcion_original>"
            Cuando el administrador edita la especialidad "<nombre_original>" cambiando su nombre a "<nombre_nuevo>" y su descripción a "<descripcion_nueva>"
            Entonces el sistema responde con el <status_code> y el "<status_text>"

            Ejemplos:
            | nombre_original | descripcion_original                                                             | nombre_nuevo | descripcion_nueva                      | status_code | status_text                       |
            | Cardiología     | Diagnóstico y tratamiento de enfermedades del corazón y el sistema circulatorio. | Cardiología  | Especialidad avanzada en cardiología.  | 200         | Especialidad editada exitosamente |
            | Pediatría       | Atención médica integral de niños y adolescentes                                 | Pediatría    | Atención integral de la salud infantil | 200         | Especialidad editada exitosamente |
            | Neurología      | Diagnóstico y tratamiento de enfermedades del sistema nervioso.                  | Neurociencia | Diagnóstico avanzado en neurociencia   | 200         | Especialidad editada exitosamente |

            Esquema del escenario: Intentar modificar una especialidad con un nombre duplicado
            Dado que la especialidad "<nombre_original>" existe en el sistema
            Y otra especialidad con el nombre "<nombre_existente>" ya está registrada
            Cuando el administrador intenta cambiar el nombre de "<nombre_original>" a "<nombre_existente>"
            Entonces el sistema responde con <status_code> y "<status_text>"

            Ejemplos:
            | nombre_original | nombre_existente | status_code | status_text                                 |
            | Cardiología     | Pediatría        | 409         | El nombre de la especialidad ya está en uso |
            | Neurología      | Ginecología      | 409         | El nombre de la especialidad ya está en uso |

            Esquema del escenario: Agregar una nueva especialidad exitosamente
            Cuando el administrador crea una nueva especialidad con el nombre "<nombre>" y la descripción "<descripcion>"
            Entonces el sistema responde con <status_code> y "<status_text>"

            Ejemplos:
            | nombre                 | descripcion                                            | status_code | status_text                      |
            | Terapia Intensiva      | Tratamiento médico en unidades de cuidados intensivos  | 200         | Especialidad creada exitosamente |
            | Medicina Esteticista   | Procedimientos médicos estéticos y rejuvenecimiento    | 200         | Especialidad creada exitosamente |
            | Medicina del Dolor     | Tratamiento del dolor crónico                          | 200         | Especialidad creada exitosamente |
            | Cirugía Reconstructiva | Procedimientos quirúrgicos para reparar tejidos        | 200         | Especialidad creada exitosamente |
            | Medicina Paliativa     | Atención médica para pacientes con enfermedades graves | 200         | Especialidad creada exitosamente |

            Esquema del escenario: Eliminar una especialidad exitosamente
            Dado que la especialidad "<nombre>" existe en el sistema
            Cuando el administrador elimina la especialidad "<nombre>"
            Entonces el sistema responde con <status_code> y "<status_text>"

            Ejemplos:
            | nombre                 | status_code | status_text                         |
            | Terapia Intensiva      | 200         | Especialidad eliminada exitosamente |
            | Medicina Estética      | 200         | Especialidad eliminada exitosamente |
            | Medicina del Dolor     | 200         | Especialidad eliminada exitosamente |
            | Cirugía Reconstructiva | 200         | Especialidad eliminada exitosamente |
            | Medicina Paliativa     | 200         | Especialidad eliminada exitosamente |