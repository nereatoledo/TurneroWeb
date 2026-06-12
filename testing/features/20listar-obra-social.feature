# language: es

Característica: Listar obras sociales

Escenario: Recuperar todas las obras sociales registradas en el sistema
Dado que existen 105 obras sociales registradas en el sistema
Cuando un usuario del sistema solicita la lista de obras sociales
Entonces el sistema responde con un JSON de las obras sociales:
"""
{
    "data": [
        {
            "id": 352,
            "nombre": "A.P.M.",
            "codigo": "APM"
        },
        {
            "id": 353,
            "nombre": "AMMA OSSACRA",
            "codigo": "AMMAOSS"
        },
        {
            "id": 354,
            "nombre": "AMPARA SALUD",
            "codigo": "AMPARASAL"
        },
        {
            "id": 355,
            "nombre": "AMTAC",
            "codigo": "AMTAC"
        },
        {
            "id": 356,
            "nombre": "AMUR ASSIMRA",
            "codigo": "AMURASS"
        },
        {
            "id": 357,
            "nombre": "ANDES SALUD",
            "codigo": "ANDESSAL"
        },
        {
            "id": 358,
            "nombre": "APROSS",
            "codigo": "APROSS"
        },
        {
            "id": 359,
            "nombre": "AVALIAN-ACA SALUD",
            "codigo": "AVALIANACA"
        },
        {
            "id": 360,
            "nombre": "BANCARIOS",
            "codigo": "BANCAR"
        },
        {
            "id": 361,
            "nombre": "BIENESTAR SALUD S.A.",
            "codigo": "BIENESTAR"
        },
        {
            "id": 362,
            "nombre": "BOREAL",
            "codigo": "BOREAL"
        },
        {
            "id": 363,
            "nombre": "C.P.C.E.",
            "codigo": "CPCE"
        },
        {
            "id": 364,
            "nombre": "CAJA NOTARIAL",
            "codigo": "CAJANOT"
        },
        {
            "id": 365,
            "nombre": "CAMI SALUD",
            "codigo": "CAMISAL"
        },
        {
            "id": 366,
            "nombre": "CAMIONEROS/LIMPIEZA/BARRIDO",
            "codigo": "CAMIONLIMB"
        },
        {
            "id": 367,
            "nombre": "CAPITANES/BAQUEANOS/FLUVIALES",
            "codigo": "CAPITBAQFLU"
        },
        {
            "id": 368,
            "nombre": "CERAMISTAS",
            "codigo": "CERAMIST"
        },
        {
            "id": 369,
            "nombre": "DASPU",
            "codigo": "DASPU"
        },
        {
            "id": 370,
            "nombre": "GALENO",
            "codigo": "GALENO"
        },
        {
            "id": 371,
            "nombre": "GASTRONOMICOS",
            "codigo": "GASTRON"
        },
        {
            "id": 372,
            "nombre": "GEA",
            "codigo": "GEA"
        },
        {
            "id": 373,
            "nombre": "GRAFICOS",
            "codigo": "GRAFICOS"
        },
        {
            "id": 374,
            "nombre": "GRIFF S.A.",
            "codigo": "GRIFF"
        },
        {
            "id": 375,
            "nombre": "GUINCHEROS Y MAQUINISTAS (SERVIRED)",
            "codigo": "GUINCHMAQ"
        },
        {
            "id": 376,
            "nombre": "IOSFA",
            "codigo": "IOSFA"
        },
        {
            "id": 377,
            "nombre": "JERARQUICOS SALUD",
            "codigo": "JERARQSAL"
        },
        {
            "id": 378,
            "nombre": "MEDIFE",
            "codigo": "MEDIFE"
        },
        {
            "id": 379,
            "nombre": "MET MEDICINA PRIVADA",
            "codigo": "METMEDPR"
        },
        {
            "id": 380,
            "nombre": "MINISTERIO DE SALUD",
            "codigo": "MINSA"
        },
        {
            "id": 381,
            "nombre": "MOSAISTAS",
            "codigo": "MOSAIST"
        },
        {
            "id": 382,
            "nombre": "MUTUAL FEDERADA",
            "codigo": "MUTFED"
        },
        {
            "id": 383,
            "nombre": "MUTUAL MEDICA RIO CUARTO",
            "codigo": "MUTMEDRC"
        },
        {
            "id": 384,
            "nombre": "NOBIS",
            "codigo": "NOBIS"
        },
        {
            "id": 385,
            "nombre": "NODO ESTRATEGICO",
            "codigo": "NODOEST"
        },
        {
            "id": 386,
            "nombre": "O.S.A.D.E.F. (EMPLEADOS DE FARMACIA)",
            "codigo": "OSADEF"
        },
        {
            "id": 387,
            "nombre": "OSBLYCA SERVIRED",
            "codigo": "OSBLYCA"
        },
        {
            "id": 388,
            "nombre": "O.S.P.A.C.P. (AMAS DE CASA)",
            "codigo": "OSPACP"
        },
        {
            "id": 389,
            "nombre": "O.S.P.I.A. (PERSONAL DE ALIMENTACION)",
            "codigo": "OSPIA"
        },
        {
            "id": 390,
            "nombre": "OSPIL",
            "codigo": "OSPIL"
        },
        {
            "id": 391,
            "nombre": "OSPIV (VESTIDO)",
            "codigo": "OSPIV"
        },
        {
            "id": 392,
            "nombre": "O.S.P.J.T.A.P.",
            "codigo": "OSPJTAP"
        },
        {
            "id": 393,
            "nombre": "O.S.P.Q.Y P. (QUIMICO-PETROL) SERVIRED",
            "codigo": "OSPQYP"
        },
        {
            "id": 394,
            "nombre": "O.S.P.R.E.R.A. SERVIRED",
            "codigo": "OSPRERA"
        },
        {
            "id": 395,
            "nombre": "O.S.P. SANIDAD",
            "codigo": "OSPSAN"
        },
        {
            "id": 396,
            "nombre": "O.S.T.I.G. SERVIRED",
            "codigo": "OSTIG"
        },
        {
            "id": 397,
            "nombre": "O.S.V.V.R.A. (GEA)",
            "codigo": "OSVVRA"
        },
        {
            "id": 398,
            "nombre": "O.S. PERS. JARDINEROS",
            "codigo": "OSPJARD"
        },
        {
            "id": 399,
            "nombre": "O.S. PERKINS",
            "codigo": "OSPERK"
        },
        {
            "id": 400,
            "nombre": "OMINT",
            "codigo": "OMINT"
        },
        {
            "id": 401,
            "nombre": "OPDEA",
            "codigo": "OPDEA"
        },
        {
            "id": 402,
            "nombre": "O.S. PERS FARMACIA",
            "codigo": "OSPFARM"
        },
        {
            "id": 403,
            "nombre": "OSAM (ITER MEDICINA)",
            "codigo": "OSAMITER"
        },
        {
            "id": 404,
            "nombre": "OSCCPTAC (CHOFERES Y CAMIONEROS)",
            "codigo": "OSCCPTAC"
        },
        {
            "id": 405,
            "nombre": "OSCEARA",
            "codigo": "OSCEARA"
        },
        {
            "id": 406,
            "nombre": "OSDE",
            "codigo": "OSDE"
        },
        {
            "id": 407,
            "nombre": "OSDEPYM",
            "codigo": "OSDEPYM"
        },
        {
            "id": 408,
            "nombre": "OSDOP/SADOP (SERVIRED)",
            "codigo": "OSDOPSAD"
        },
        {
            "id": 409,
            "nombre": "OSEIV (VIDRIO)",
            "codigo": "OSEIV"
        },
        {
            "id": 410,
            "nombre": "OSFATLYF",
            "codigo": "OSFATLYF"
        },
        {
            "id": 411,
            "nombre": "OSFE",
            "codigo": "OSFE"
        },
        {
            "id": 412,
            "nombre": "OSMATA (SANITAS)",
            "codigo": "OSMATA"
        },
        {
            "id": 413,
            "nombre": "OSMTT (SERVIRED)",
            "codigo": "OSMTT"
        },
        {
            "id": 414,
            "nombre": "OSPAT (SANOS)",
            "codigo": "OSPAT"
        },
        {
            "id": 415,
            "nombre": "OSPATCA",
            "codigo": "OSPATCA"
        },
        {
            "id": 416,
            "nombre": "OSPE",
            "codigo": "OSPE"
        },
        {
            "id": 417,
            "nombre": "OSPEC",
            "codigo": "OSPEC"
        },
        {
            "id": 418,
            "nombre": "OSPECON",
            "codigo": "OSPECON"
        },
        {
            "id": 419,
            "nombre": "OSPEDYC",
            "codigo": "OSPEDYC"
        },
        {
            "id": 420,
            "nombre": "OSPES (PERS. EST. SERVICIO)",
            "codigo": "OSPES"
        },
        {
            "id": 421,
            "nombre": "OSPEVIC",
            "codigo": "OSPEVIC"
        },
        {
            "id": 422,
            "nombre": "OSPIC",
            "codigo": "OSPIC"
        },
        {
            "id": 423,
            "nombre": "OSPIF",
            "codigo": "OSPIF"
        },
        {
            "id": 424,
            "nombre": "OSPLYF (INTERIOR)",
            "codigo": "OSPLYF"
        },
        {
            "id": 425,
            "nombre": "OSPLUF LYF CBA",
            "codigo": "OSPLUFLYF"
        },
        {
            "id": 426,
            "nombre": "OSPOCE INTEGRAL",
            "codigo": "OSPOCEINT"
        },
        {
            "id": 427,
            "nombre": "OSSOELSAC",
            "codigo": "OSSOELSAC"
        },
        {
            "id": 428,
            "nombre": "OSTAMMA/MARADONA SALUD",
            "codigo": "OSTAMMA"
        },
        {
            "id": 429,
            "nombre": "PAMI",
            "codigo": "PAMI"
        },
        {
            "id": 430,
            "nombre": "PARQUE SALUD",
            "codigo": "PARQUESAL"
        },
        {
            "id": 431,
            "nombre": "PASTELEROS/CONFITEROS",
            "codigo": "PASTCONF"
        },
        {
            "id": 432,
            "nombre": "O.S. PERS. CARGA Y DESCARGA",
            "codigo": "OSPCARGA"
        },
        {
            "id": 433,
            "nombre": "PREME",
            "codigo": "PREME"
        },
        {
            "id": 434,
            "nombre": "PREMED GRUPO AZUL",
            "codigo": "PREMEDGA"
        },
        {
            "id": 435,
            "nombre": "PREMEDIC",
            "codigo": "PREMEDIC"
        },
        {
            "id": 436,
            "nombre": "PREVENCION SALUD",
            "codigo": "PREVSAL"
        },
        {
            "id": 437,
            "nombre": "PRO SALUD",
            "codigo": "PROSALUD"
        },
        {
            "id": 438,
            "nombre": "RED ARG DE SALUD",
            "codigo": "REDARGSAL"
        },
        {
            "id": 439,
            "nombre": "SAJU SALUD",
            "codigo": "SAJUSAL"
        },
        {
            "id": 440,
            "nombre": "SANARTE",
            "codigo": "SANARTE"
        },
        {
            "id": 441,
            "nombre": "SANOS NACIONAL",
            "codigo": "SANOSNAC"
        },
        {
            "id": 442,
            "nombre": "SCIS",
            "codigo": "SCIS"
        },
        {
            "id": 443,
            "nombre": "SE.ME.GER. S.A.",
            "codigo": "SEMEGER"
        },
        {
            "id": 444,
            "nombre": "SERVICIO PENITENCIARIO FEDERAL",
            "codigo": "SERPENFED"
        },
        {
            "id": 445,
            "nombre": "SERVIRED",
            "codigo": "SERVIRED"
        },
        {
            "id": 446,
            "nombre": "SINDICATO DE TV",
            "codigo": "SINDTV"
        },
        {
            "id": 447,
            "nombre": "SIPSSA",
            "codigo": "SIPSSA"
        },
        {
            "id": 448,
            "nombre": "SANCOR SALUD",
            "codigo": "SANCORSAL"
        },
        {
            "id": 449,
            "nombre": "SUME SALUD",
            "codigo": "SUMESAL"
        },
        {
            "id": 450,
            "nombre": "SWISS MEDICAL",
            "codigo": "SWISSMED"
        },
        {
            "id": 451,
            "nombre": "UNIMED",
            "codigo": "UNIMED"
        },
        {
            "id": 452,
            "nombre": "UNION PERSONAL",
            "codigo": "UNIONPERS"
        },
        {
            "id": 453,
            "nombre": "UTA",
            "codigo": "UTA"
        },
        {
            "id": 454,
            "nombre": "VISITAR",
            "codigo": "VISITAR"
        },
        {
            "id": 455,
            "nombre": "VITIVINICOLAS",
            "codigo": "VITIVINI"
        },
        {
            "id": 456,
            "nombre": "WILLIAM HOPE SALUD",
            "codigo": "WILLIAMH"
        }
    ],
    "message": "Obras sociales recuperadas exitosamente",
    "status": 200
}

"""