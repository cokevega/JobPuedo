/*
Script SQL para iniciar la base de datos e insertar valores de ejemplo con los que poder ver el funcionamiento de la aplicación.
No contiene imágenes de perfil para los usuarios.
*/

/*Database*/
DROP DATABASE IF EXISTS `JobPuedo`;
CREATE DATABASE `JobPuedo`;
use `JobPuedo`;

/*Categories*/
DROP TABLE IF EXISTS `Categories`;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `active` boolean NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (1,'Arquitectura','Habilidades para diseñar, dirigir y construir proyectos arquitectónicos, que pueden ir desde diseños en pequeña escala (como casas), hasta gran escala (como el planeamiento de una ciudad).',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (2,'Contablidad/Finanzas','Actividades para mantener oportuna y correcta la aplicación del sistema contable y presupuestal, asi como mantener en forma eficiente la programación y pago de los egresos, así como la respectiva creación de pasivos.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (3,'Computacion/TI','Trabajos de capturistas, manejo de paqueteria de software, diseño de redes, telecomunicaciones, etc.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (4,'Ingenieria electronica','Trabajos con aplicación en la industria, telecomunicaciones, en el diseño y análisis de instrumentación electrónica, microcontroladores y microprocesadores. ',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (5,'Recursos Humanos','Administración de nóminas, pagas extra de los empleados, supervisar el trabajo de los empleados, determinar las necesidades del personal.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (6,'Servicio y atencion al cliente','Actividades relacionadas con ofrecer servicios y atencion a los clientes de forma efectiva.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (7,'Logistica/transportes','Trabajos relacionados con capacidad de observación, buena memoria, habilidad numérica y verbal, razonamiento concreto y abstracto, imaginación e inventiva, habilidad para el manejo de instrumentos y material de laboratorio, capacidad de adaptación social y trabajo de campo.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (8,'Desarrollo de software','Profesionistas capaces de analizar, diseñar y mejorar estratégicamente proyectos de sistemas de software mediante la aplicación de procesos, modelos, herramientas y estándares de calidad en su desarrollo.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (9,'Diseño','Crear conceptos visuales para publicidad, reunirse con clientes para conocer el presupuesto del proyecto, asesorar a los clientes para crear estrategias de publicidad visual, liderar equipos de trabajo, diseñar logotipos.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (10,'Comunicaciones','Profesionales que se encargan de escribir las noticias que usualmente vemos en las revistas, periódicos e incluso en la televisión, muchos incluso, se dedican a escribir historias en Blogs y en los diferentes medios de comunicación escrita.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (11,'Mercadotecnia','Investigación de mercados, programación y desarrollo del producto, fijación de precios, canales de distribución y logística. Comunicación integral: publicidad, comunicación e imagen, relaciones públicas, marketing directo, promoción, etc.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (12,'Ventas','Habilidades para supervisar y dirigir las actividades de una oficina o de un departamento de Ventas. Coordinar y monitorear el trabajo de los empleados a su cargo.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (13,'Publicidad','Habilidadeds para planificar, dirigir y coordinar las actividades de publicidad y relaciones públicas de la empresa u organización. Diseñar y planificar campañas publicitarias. Dirigir y gestionar las actividades del personal de publicidad y relaciones públicas.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (14,'Gerencia/Administracion','Profesionistas capaces para programar, organizar, dirigir, controlar y supervisar las actividades de personal, tesorería, contabilidad y costos, logística y servicios internos y de mantenimiento.',true);
INSERT INTO `categories`(`id`,`name`,`description`,`active`) VALUES (15,'Educación','Funciones de docencia de carácter profesional que implica la realización directa de los procesos sistemáticos de enseñanza - aprendizaje, lo cual incluye el diagnóstico, la planificación, la ejecución y la evaluación de los mismos procesos y sus resultados.',true);

/*Roles*/
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `roles` VALUES (1,'ROLE_USER');
INSERT INTO `roles` VALUES (2,'ROLE_ENTERPRISE');
INSERT INTO `roles` VALUES (3,'ROLE_ADMIN');

/*Images*/
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
	`id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `type` VARCHAR(30) NOT NULL,
    `data` MEDIUMBLOB NOT NULL,
    PRIMARY KEY(`id`)
);

/*Users*/
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `last_name` varchar(50),
  `born` date,
  `phone` VARCHAR(20),
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `registered_at` date NOT NULL,
  `image_id` VARCHAR(50),
  `enterprise` boolean NOT NULL,
  `contact_name` VARCHAR(50),
  `contact_last_name` VARCHAR(45),
  `cif` VARCHAR(10),
  `description` TEXT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_users_files1_idx` (`image_id`),
  CONSTRAINT `fk_users_files1_idx` FOREIGN KEY (`image_id`) REFERENCES `files` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`phone`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (1,'Jorge','García Vega','1996-07-12','685725198','admin@admin.com','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2019-06-10',false);
INSERT INTO `users` (`id`,`name`,`email`,`password`,`status`,`registered_at`,`enterprise`,`contact_name`,`contact_last_name`,`cif`,`description`) VALUES(2,'Iman Corp','lbueno@imancorp.es','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2020-07-01',true,'Laura','Bueno Andrés','B12345678','Somos una Empresa de Trabajo Temporal, especializada en el sector del metal y en repartidores a domicilio. No obstante lo anterior, somos igualmente eficientes para otros sectores.');
INSERT INTO `users` (`id`,`name`,`email`,`password`,`status`,`registered_at`,`enterprise`,`contact_name`,`contact_last_name`,`cif`,`description`) VALUES(4,'Burger King','rrhh@burgerking.es','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2019-03-02',true,'Lorena','Martín Garrido','A12345678','Desde principios del siglo XX llevamos cuidando tanto a nuestros clientes, como a nuestros trabajadores. Burger King mantiene un riguroso respeto a los derechos humanos y laborales. Únete a nuestro equipo y compruébalo por ti mismo.');
INSERT INTO `users` (`id`,`name`,`email`,`password`,`status`,`registered_at`,`enterprise`,`contact_name`,`contact_last_name`,`cif`,`description`) VALUES(5,'Indra','rrhh@indra.es','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2020-04-02',true,'Víctor','González Fernández','B23456789','Consultora tecnológica de ámbito nacional. Hemos trabajado con el Gobierno de España en multitud de proyectos, nuestro prestigio nos avala.');
INSERT INTO `users` (`id`,`name`,`email`,`password`,`status`,`registered_at`,`enterprise`,`contact_name`,`contact_last_name`,`cif`,`description`) VALUES(6,'Biochemistry Lab','gerencia@biochem.com','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2020-06-17',true,'Margarita','Sáez Flores','C43219876','Startup biotecnológica en pleno crecimiento. Actualmente trabajando en importantes investigaciones sobre la bioquímica de la conducta. Nuestros principales valores son el trabajo en equipo y el respeto al prójimo.');
INSERT INTO `users` (`id`,`name`,`email`,`password`,`status`,`registered_at`,`enterprise`,`contact_name`,`contact_last_name`,`cif`,`description`) VALUES(7,'Mariscos Recio, S.L.','gambasfrescas@mariscosrecio.net','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2018-08-14',true,'Antonio','Recio Matamoros','A24689753','Soy mayorista de pescados y mariscos, pero no limpio pescado. Siempre en busca de nuevos trabajadores a los que ofrecer contratos leoninos, preferiblemente sumisos y dóciles.');
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (3,'Patricia','Torres Izquierdo','1992-02-01','patritorizq@gmail.com','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2018-06-22',false);
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (8,'Lucas','Méndez García','1995-02-01','lucasmendez@hotmail.com','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2019-03-05',false);
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (9,'Pablo','Ortega Smith','1876-09-18','pabloortega@gmail.com','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2020-06-27',false);
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (10,'Egoitz','Llano Llano','1988-11-24','egollano@yahoo.net','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2019-04-30',false);
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (11,'Oscarín','Ramalín Puigdemont','1993-05-19','ramalincatalanin@proces.bcn','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2018-12-26',false);
INSERT INTO `users` (`id`,`name`,`last_name`,`born`,`email`,`password`,`status`,`registered_at`,`enterprise`) VALUES (12,'Jessica','García Argüelles','1954-12-31','jessicagarcia@hotmail.es','$2a$10$D8wfuXFlh4TIrj2AFUjaGeZth1oq3BLruSvt5YkJBL9Wa9S3qt3Vi',1,'2021-01-28',false);

/*Skills*/
DROP TABLE IF EXISTS `skills`;
CREATE TABLE `skills` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `level` ENUM('Básico','Medio','Avanzado') NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY(`id`),
	KEY `fk_skills_users1_idx` (`user_id`),
	CONSTRAINT `fk_skills_users1_idx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `skills` VALUES(1,'Paquete Office (Microsoft)','Básico',3);
INSERT INTO `skills` VALUES(2,'ContaPlus','Medio',3);
INSERT INTO `skills` VALUES(3,'Angular','Avanzado',1);
INSERT INTO `skills` VALUES(4,'Laravel','Básico',1);
INSERT INTO `skills` VALUES(5,'Spring','Medio',8);
INSERT INTO `skills` VALUES(6,'Flutter','Avanzado',8);
INSERT INTO `skills` VALUES(7,'Java','Básico',9);
INSERT INTO `skills` VALUES(8,'Git & GitHub','Medio',9);
INSERT INTO `skills` VALUES(9,'Jira','Avanzado',10);
INSERT INTO `skills` VALUES(10,'React','Básico',10);
INSERT INTO `skills` VALUES(11,'Angular','Medio',11);
INSERT INTO `skills` VALUES(12,'Vue.js','Avanzado',11);
INSERT INTO `skills` VALUES(13,'Node.js','Básico',12);
INSERT INTO `skills` VALUES(14,'WordPress','Medio',12);

/*Languages*/
DROP TABLE IF EXISTS `languages`;
CREATE TABLE `languages` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `level` ENUM('Básico','Medio','Avanzado') NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_languages_users1_idx` (`user_id`),
    CONSTRAINT `fk_languages_users1_idx` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`) on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `languages` VALUES(1,'Inglés','Básico',1);
INSERT INTO `languages` VALUES(2,'Francés','Medio',1);
INSERT INTO `languages` VALUES(3,'Chino mandarín','Avanzado',3);
INSERT INTO `languages` VALUES(4,'Italiano','Básico',3);
INSERT INTO `languages` VALUES(5,'Tailandés','Básico',8);
INSERT INTO `languages` VALUES(6,'Portugués','Medio',8);
INSERT INTO `languages` VALUES(7,'Japonés','Avanzado',9);
INSERT INTO `languages` VALUES(8,'Quechua','Básico',9);
INSERT INTO `languages` VALUES(9,'Catalán','Medio',10);
INSERT INTO `languages` VALUES(10,'Alemán','Avanzado',10);
INSERT INTO `languages` VALUES(11,'Húngaro','Básico',11);
INSERT INTO `languages` VALUES(12,'Ruso','Medio',11);
INSERT INTO `languages` VALUES(13,'Polaco','Avanzado',12);
INSERT INTO `languages` VALUES(14,'Rumano','Básico',12);

/*Experiences*/
DROP TABLE IF EXISTS `experiences`;
CREATE TABLE `experiences` (
	`id` int NOT NULL AUTO_INCREMENT,
    `begin` date NOT NULL,
    `end` date,
    `enterprise` VARCHAR(50) NOT NULL,
    `comments` TEXT,
    `position` VARCHAR(50) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_experiences_users1_idx` (`user_id`),
    CONSTRAINT `fk_experiences_users1_idx` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`) on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `experiences` VALUES(1,'2018-10-01','2019-05-01','Café Boto','Mi primera experiencia laboral. Manejo de bandeja, atención al cliente en barra y mesa. Tareas de reposición y limpieza para cierre del local.','Camarero','Oviedo',1);
INSERT INTO `experiences` VALUES(2,'2021-07-01',null,'Neosentec','Desarrollador de Realidad Aumentada empleando la librería Three.js de JavaScript, Angular y Onirix, un CMS de desarrollo propio de la empresa con SDK para integración en aplicaciones web.','Junior Developer','Llanera',1);
INSERT INTO `experiences` VALUES(3,'2018-09-01','2019-12-15','Café DiNata','Manejo de bandeja, atención al cliente en barra, interior y terraza. Tareas de reposición y limpieza para cierre del local.','Auxiliar de camarera','Oviedo',3);
INSERT INTO `experiences` VALUES(4,'2020-02-01',null,'Asesoría Hermanos García','Funciones de asesoría en materia fiscal, laboral y contable a los clientes, incluyendo la llevanza de su contabilidad a aquellos que lo solicitaran.','Auxiliar administrativa','Oviedo',3);
INSERT INTO `experiences` VALUES(5,'2012-06-18','2017-09-01','Fontanería Hermanos Nieto','Labores de fontanería a domicilio, incluyendo emergencias los fines de semana. Máxima profesionalidad y eficiencia siempre en mi trabajo.','Oficial 2º de fontanería','Donosti',8);
INSERT INTO `experiences` VALUES(6,'2018-11-13',null,'Construcciones Vidal','Albañilería de la más básica a la más avanzada. Desde cargar sacos de cemento hasta la construcción del Empire State entero yo solo.','Oficial 1º de albañilería','Valencia',8);
INSERT INTO `experiences` VALUES(7,'2019-05-14','2020-01-12','Randstad','Gestión de altas, bajas, nóminas, accidentes laborales y labores de marketing y fidelización con los clientes.','Técnico de Recursos Humanos','Gijón',9);
INSERT INTO `experiences` VALUES(8,'2021-01-02',null,'Líder IT','Desarrollo de aplicaciones web y móviles siguiendo los requisitos funcionales marcados por el cliente. Siempre cumpliendo objetivos y plazos.','Desarrollador Web','Alabama',9);
INSERT INTO `experiences` VALUES(9,'2015-04-03','2016-07-09','Parlament de Catalunya','Traductor del español al catalán en ruedas de prensa, sesiones en el Parlment y demás actos oficiales de aquellos diputados que solicitaran mis servicios.','Traductor','Barcelona',10);
INSERT INTO `experiences` VALUES(10,'2018-04-01','2020-07-25','Google Inc.','Desarrollador de Inteligencia Artificial en busca de la mejora de los algoritmos para determinar los gustos del usuario y ofrecerle la mejor experiencia de navegación posible.','IA Developer','California',10);
INSERT INTO `experiences` VALUES(11,'2017-11-21','2020-01-19','Copistería Low Copy','Atención al cliente. Realización de fotocopias y encuadernaciones, así como inventariado mensual.','Avilés','Auxiliar de tienda',11);
INSERT INTO `experiences` VALUES(12,'2020-03-22','2021-06-15','Comunications, S.A.','Gestión de los recursos humanos disponibles en el Departamento de Marketing y supervisión de los proyectos finales antes de entregárselos al cliente','Responsable del Departamento de Marketing','Sevilla',11);
INSERT INTO `experiences` VALUES(13,'2019-10-18','2021-04-28','Securitas Direct','Comercial de trato personal con cliente, con la encargada de la captación de nuevos clientes, así como la fidelización de los ya incorporados a la empresa.','Comercial','Madrid',12);
INSERT INTO `experiences` VALUES(14,'2021-06-12',null,'Mariscos Recio, S.L.','Atención al cliente en pescadería, con limpieza del género y empaquetado del mismo.','Pescadera','Taiwán',12);

/*Education*/
DROP TABLE IF EXISTS `education`;
CREATE TABLE `education` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `begin` DATE NOT NULL,
    `end` DATE,
    `title` VARCHAR(100) NOT NULL,
    `school` VARCHAR(100) NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_education_users1_idx` (`user_id`),
    CONSTRAINT `fk_education_users1_idx` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `education` VALUES(1,'Contabilidad y Finanzas','2014-09-01',null,'Grado Universitario','Universidad de Oviedo',3);
INSERT INTO `education` VALUES(2,'CFGS Contabilidad','2012-09-01','2014-07-01','FP Superior','Centro de Formación Profesional de Avilés',3);
INSERT INTO `education` VALUES(3,'Grado en Biotecnología','2014-09-11','2018-07-01','Grado Universitario','Universidad de Oviedo',1);
INSERT INTO `education` VALUES(4,'CFGS Desarrollo de Aplicaciones Web','2019-09-18','2021-07-01','FP Superior','I.E.S. Monte Naranco',1);
INSERT INTO `education` VALUES(5,'Sistemas microinformáticos y redes','2010-09-01','2012-07-01','FP Medio','I.E.S. Doctor Fleming',8);
INSERT INTO `education` VALUES(6,'Grado en Periodismo','2014-09-11','2019-02-01','Grado Universitario','Universidad de Salamanca',8);
INSERT INTO `education` VALUES(7,'Grado en Derecho','2013-09-19','2019-10-01','Grado Universitario','Universidad de Granada',9);
INSERT INTO `education` VALUES(8,'Máster en Derecho Penal','2019-09-10',null,'Postgrado','Universidad de Oviedo',9);
INSERT INTO `education` VALUES(9,'Fontanería','2015-09-09','2017-07-11','FP Básico','Centro de Formación Profesional de Avilés',10);
INSERT INTO `education` VALUES(10,'CFGS Desarrollo de Aplicaciones Multiplataforma','2018-09-08',null,'FP Superior','I.E.S. Doctor Fleming',10);
INSERT INTO `education` VALUES(11,'Filología Inglesa','2015-09-09','2019-07-01','Grado Universitario','Universidad de Navarra',11);
INSERT INTO `education` VALUES(12,'Máster en filología inglesa','2019-09-11','2020-07-19','Postgrado','Universidad de Zamora',11);
INSERT INTO `education` VALUES(13,'Marketing y Comunicaciones','2016-09-12','2020-06-21','Grado Universitario','Universidad Complutense de Madrid',12);
INSERT INTO `education` VALUES(14,'Turismo','2020-09-18',null,'FP Superior','I.E.S. Puente Viejo',12);

/*Offers*/
DROP TABLE IF EXISTS `offers`;
CREATE TABLE `offers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` text NOT NULL,
  `date` date NOT NULL,
  `salary` double,
  `status` enum('Created','Active','Deleted') NOT NULL,
  `details` text,
  `category_id` int NOT NULL,
  `enterprise_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_offers_categories1_idx` (`category_id`),
  CONSTRAINT `fk_offers_categories1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  KEY `fk_offers_users1_idx` (`enterprise_id`),
  CONSTRAINT `fk_offers_users1` FOREIGN KEY (`enterprise_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `Offers` VALUES (1,'Analista Contable.','Importante Empresa dedicada a ofrecer servicios de Consultoria en Recursos Humanos, Jurídico y Finanzas te invita a formar parte de su equipo como Analista Contable.','2019-05-05',8500,'Active','<p><strong>Requisitos:</strong><br />Edad: 24 a 35 a&ntilde;os<br />Escolaridad: Licenciatura en Contabilidad o af&iacute;n</p>\r\n<p><strong>Experiencia m&iacute;nima de 2 a&ntilde;os en:</strong><br />1. Descarga de facturas del sistema de CXP y/o SAT<br />2. Registro de gastos<br />3. Elaboraci&oacute;n de papel de trabajo ISR e IVA<br />4. Presentaci&oacute;n de impuestos federales en el SAT<br />5. Elaboraci&oacute;n de papel de trabajo declaraci&oacute;n informativa (DIOT)<br />6. Presentaci&oacute;n de DIOT en el SAT<br />7. Seguimiento a CXC<br />8. Registro de pagos y complementos de pagos<br />9. Confronta vs el SAT para registro de facturas pendientes<br />10. Integraci&oacute;n de CXP y CXC<br />11. Arqueo de caja<br />12. Depuraci&oacute;n de cuentas<br />13. Cuadre de nominas<br />14. Seguimiento a pagos anticipados<br />15. Registro de depreciaci&oacute;n mensual<br />16. Registro de devengaci&oacute;n mensual<br />17. Registro de rembolsos<br />18. Conciliaciones bancarias</p>\r\n<p>Ofrecemos Prestaciones de Ley + Bono de Asistencia del 5% + Vales de Despensa del 5%.</p>\r\n<p><strong>Requerimientos</strong><br />Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura<br />A&ntilde;os de experiencia: 2<br />Idiomas: Ingl&eacute;s ,Espa&ntilde;ol<br />Edad: entre 24 y 35 a&ntilde;os<br />Conocimientos: LibreOffice<br />Disponibilidad de viajar: No<br />Disponibilidad de cambio de residencia: No</p>\r\n<p>&nbsp;</p>',2,2);
INSERT INTO `Offers` VALUES (2,'Administrador de datos en la nube ','Únete al Socio Logístico con mayor presencia en México, Soft Technologies te invita a formar parte de su gran equipo de trabajo como Administrador de datos en la nube.','2019-05-05',14200,'Active','<p><span style=\"color: #0000ff;\"><strong>REQUISITOS</strong></span></p>\r\n<p>Escolaridad: Lic. trunca o pasante en Ing en Sistemas Computacionales o afin.<br />Experiencia de 2 a&ntilde;os en:</p>\r\n<ul>\r\n<li>2 a&ntilde;os como ingeniero de infraestructura en tecnolog&iacute;as Microsoft, experiencia en soluciones de Infraestructura (Hardware/Software).</li>\r\n<li>2 a&ntilde;os administrando servidores en centros de datos.</li>\r\n</ul>\r\n<p><span style=\"color: #0000ff;\"><strong>ESPEC&Iacute;FICOS</strong></span></p>\r\n<ul>\r\n<li>Administraci&oacute;n monitoreo y mantenimiento de servicios en la nube Microsoft Azure.</li>\r\n<li>Administraci&oacute;n de servicios y proveedores de nube ITIL Foundations.</li>\r\n<li>Herramientas y Tecnolog&iacute;as de Virtualizaci&oacute;n Autoestudio.</li>\r\n<li>Herramientas de Monitoreo para servicios en la Nube o en Sitio.</li>\r\n<li>Esquemas de alta disponibilidad para nubes, cluster dispersos geogr&aacute;ficamente.</li>\r\n<li>Servicios de Nube: SaaS, PaaS, IaaS.</li>\r\n<li>Conocimientos b&aacute;sicos de Redes.</li>\r\n<li>Administraci&oacute;n de bases de datos en la nube SQL Azure.</li>\r\n<li>Conocimiento de consumo de costos de servicios en la nube.</li>\r\n<li>Herramientas de Monitoreo para nube.</li>\r\n<li>VPN.</li>\r\n<li>Almacenamiento en Nube.</li>\r\n</ul>\r\n<p>&nbsp;<span style=\"color: #0000ff;\"><strong>OFRECEMOS</strong></span></p>\r\n<ul>\r\n<li>ATRACTIVO SUELDO + prestaciones superiores a las de ley.</li>\r\n<li>Excelente ambiente de trabajo.</li>\r\n<li>Zonas de trabajo: Condesa.</li>\r\n<li>Horario Tiempo Completo: Lunes a Viernes: 09:00-06:00 con disponibilidad de horario.</li>\r\n</ul>\r\n<p><span style=\"color: #0000ff;\"><strong>ADICIONALES</strong></span></p>\r\n<ul>\r\n<li>Disponibilidad de viajar: No</li>\r\n<li>Disponibilidad de cambio de residencia: No</li>\r\n</ul>',3,4);
INSERT INTO `Offers` VALUES (3,'Coordinador de Marketing','TE INVITAMOS A UNIRTE A MARKETING TEC2000 COMO COORDINADOR O COORDINADORA DE MARKETING. SOMOR UNA EMPRESA DE INNOVACION EN EL SECTOR METALMECANICO E IMPORTACIONES, EXCELENTE OPORTUNIDAD DE DESARROLLO Y APLICACION DE CONOCIMIENTOS.','2019-05-06',7900,'Active','<p><strong><span style=\"background-color: #ccffcc;\">BUSCAMOS LIDER QUE GUSTE DE RETOS PARA:</span></strong></p>\r\n<ul>\r\n<li>DISE&Ntilde;AR, PLANIFICAR ELABORAR E INSTAURAR LOS PLANES DE MARKETING DE LA EMPRESA.</li>\r\n<li>DESARROLLAR PLAN PARA IMAGEN CORPORATIVA.</li>\r\n<li>PLANEACI&Oacute;N, EJECUCI&Oacute;N Y CONTROL DE CAMPA&Ntilde;AS 360&deg; (IMPLEMENTACI&Oacute;N, EJECUCI&Oacute;N, M&Eacute;TRICAS, AN&Aacute;LISIS, RESULTADOS, PRESENTACIONES EJECUTIVAS, PRESUPUESTOS).</li>\r\n<li>DAR SOPORTE AL &Aacute;REA DE VENTAS EN CUANTO A ESTRATEGIAS, POL&Iacute;TICAS, CANALES, PUBLICIDAD, M&Aacute;NEJO DE REDES SOCIALES, INVESTIGACI&Oacute;N DE MERCADOS, SEGUIMIENTO A RESULTADOS DE &Aacute;REA COMERCIAL.</li>\r\n<li>HACER INVESTIGACIONES COMERCIALES DE LOS PRODUCTOS EXISTENTES O NUEVOS, REALIZANDO EL ESTUDIO DE LAS DEBILIDADES, AMENAZAS, FORTALEZAS Y OPORTUNIDADES DE LOS MISMOS EN EL MERCADO.</li>\r\n<li>SE ENCARGAR&Aacute; DE CAPTAR NUEVOS CLIENTES Y DE MANTENER LOS QUE YA EXISTEN.</li>\r\n<li>DESARROLLAR E IMPLANTAR UNA ESTRATEGIA DE COMUNICACI&Oacute;N INTEGRAL DE CONFORMIDAD CON LA MISI&Oacute;N Y PLAN DE MARKETING.</li>\r\n<li>LLEVAR A CABO LA PRESENTACI&Oacute;N DE LA COMPA&Ntilde;&Iacute;A.</li>\r\n<li>COORDINAR SU ACTIVIDAD CON OTROS DEPARTAMENTOS.</li>\r\n<li>ORGANIZAR Y DEFINIR LOS EVENTOS DE LA EMPRESA.</li>\r\n<li>INTRODUCCI&Oacute;N DE NUEVOS PRODUCTOS</li>\r\n</ul>\r\n<p><strong><span style=\"background-color: #ccffcc;\">REQUERIMIENTOS</span></strong></p>\r\n<ul>\r\n<li>EDUCACI&Oacute;N M&Iacute;NIMA: EDUCACI&Oacute;N SUPERIOR - LICENCIATURA</li>\r\n<li>A&Ntilde;OS DE EXPERIENCIA: 1</li>\r\n<li>EDAD: ENTRE 23 Y 40 A&Ntilde;OS</li>\r\n<li>CONOCIMIENTOS: LIBREOFFICE</li>\r\n<li>LICENCIAS DE CONDUCIR: AUTOMOVIL</li>\r\n<li>DISPONIBILIDAD DE VIAJAR: NO</li>\r\n<li>DISPONIBILIDAD DE CAMBIO DE RESIDENCIA: NO</li>\r\n</ul>\r\n<p><strong><span style=\"background-color: #ccffcc;\">COMPETENCIAS</span></strong></p>\r\n<ol>\r\n<li>ENFOCADO A RESULTADOS</li>\r\n<li>ENFOQUE ESTRAT&Eacute;GICO</li>\r\n<li>TOMA DE DECISIONES</li>\r\n<li>APEGO A NORMAS</li>\r\n<li>TRABAJO EN EQUIPO</li>\r\n</ol>\r\n<p><span style=\"color: #ff0000;\">SUELDO COMPETITIVO.</span><br /><span style=\"color: #ff0000;\">HORARIO DE 9:00 A 7:00 DE LUNES A VIERNES</span><br /><span style=\"color: #ff0000;\">ZONA DE TRABAJO: VALLEJO</span><br /><span style=\"color: #ff0000;\">INTERESADOS POSTULARSE POR ESTE MEDIO</span></p>',11,5);
INSERT INTO `Offers` VALUES (4,'Gerente de Recursos Humanos','Importante empresa líder en su giro solicita por expansión Gerente de Recursos Humanos para llevar el control de personal, supervisión del reclutamiento y los programas de capacitación.','2019-05-06',17000,'Active','<p><strong>IMPORTANTE EMPRESA L&Iacute;DER EN SU GIRO SOLICITA POR EXPANSI&Oacute;N</strong><br /><br />EJECUTIVO DE RECLUTAMIENTO BILING&Uuml;E<br /><br /><span style=\"color: #ff0000;\"><strong>REQUISITOS:</strong></span></p>\r\n<ol>\r\n<li>LIC. EN PSICOLOGIA, ADMINISTRACI&Oacute;N O AFIN</li>\r\n<li>EDAD 22 A 35 A&Ntilde;OS</li>\r\n<li>EXPERIENCIA MINIMA DE 2 A&Ntilde;OS EN PUESTO SIMILAR</li>\r\n<li>INGLES AVANZADO</li>\r\n<li>MANEJO DE OFFICE</li>\r\n<li>DISPONIBILIDAD INMEDIATA</li>\r\n<li>EXCELENTE PRESENTACION</li>\r\n</ol>\r\n<p><span style=\"color: #ff0000;\"><strong><span style=\"color: #ff0000;\">FUNCIONES:</span></strong></span></p>\r\n<ul>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">ADMINISTRACI&Oacute;N DE PERSONAL</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">ALTAS, BAJAS, MODIFICACI&Oacute;N DE SALARIO</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">ELABORACI&Oacute;N Y GESTI&Oacute;N DE INCIDENCIAS</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">ELABORACI&Oacute;N Y REVISI&Oacute;N DE CONTRATOS</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">PROCESO, ELABORACI&Oacute;N Y C&Aacute;LCULO DE PRE-N&Oacute;MINA</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">SEGUIMIENTO Y OBLIGACIONES ANTE EL IMSS, INFONAVIT, ISR</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">CONTROL Y ENTREGA DE CREDENCIALES</span> </span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">GESTIONAR FIRMAS DE CONTRATOS.</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">ELABORACI&Oacute;N Y REVISI&Oacute;N DE REPORTES.</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">CONTROL DE KPI&acute;S</span></span></li>\r\n<li><span style=\"color: #ff0000;\"><span style=\"color: #000000;\">GESTIONAR Y DAR SEGUIMIENTO EN FORMA Y TIEMPO A CUALQUIER SOLICITUD RELACIONADA A LOS CLIENTES Y COLABORADORES.</span><strong><br /></strong></span></li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>OFRECEMOS:</strong></span></p>\r\n<ul>\r\n<li>PRESTACIONES DE LEY</li>\r\n<li>VALES DE DESPENSA</li>\r\n<li>DIAS Y HORARIO DE TRABAJO:LUNES A VIERNES DE 9:00 HRS A 18:00 HRS</li>\r\n<li>ZONA DE TRABAJO: ALVARO OBREGON, CDMX</li>\r\n</ul>\r\n<p><em><strong><span style=\"color: #0000ff;\">INTERESADOS QUE CUMPLAN CON EL PERFIL SOLICITADO:</span></strong></em><br /><em><strong><span style=\"color: #0000ff;\">ENVIAR CV ACTUALIZADO AL CORREO POR ESTE MISMO MEDIO Y EN BREVE ESTAREMOS AGENDADO UNA ENTREVISTA. EN ASUNTO PONER NOMBRE DE LA VACANTE.</span></strong></em></p>\r\n<p>&nbsp;</p>',5,6);
INSERT INTO `Offers` VALUES (5,'Jefe de Almacén','Se requiere Jefe de Almacén y logística en empresa líder a nivel nacional dedicada a la distribución de medicamentos de alta especialidad en el sector gobierno, organismos descentralizados y hospitales privados.','2019-05-07',12400,'Deleted','<p><span style=\"background-color: #00ccff;\"><em>Requisitos:</em></span></p>\r\n<ol>\r\n<li>Titulado en Ing. Industrial, Ing. Qu&iacute;mico, Q.F.B., Q.F.I., Lic. En Farmacia y/o carrera af&iacute;n.</li>\r\n<li>5 a&ntilde;os Experiencia en posici&oacute;n similar: Manejo de almacenes en Red Fr&iacute;a y distribuci&oacute;n de insumos para la salud.</li>\r\n<li>Trato con clientes, proveedores y con personal a cargo.</li>\r\n</ol>\r\n<p><span style=\"background-color: #00ccff;\"><em>Actividades:</em></span></p>\r\n<ul>\r\n<li>Cumplir con los requisitos Normativos y de los Clientes en la recepci&oacute;n, almacenamiento, reabasto, etiquetado, surtido y embarque de los insumos para la salud.</li>\r\n<li>Reportar continuamente el desempe&ntilde;o de los procesos a su cargo para asegurar el cumplimiento en cantidad, calidad, costo y tiempo de la operaci&oacute;n.</li>\r\n<li>Garantizar el ingreso de los insumos para la salud en el sistema SAP y su revisi&oacute;n f&iacute;sica, verificando los reportes de ingresos, as&iacute; como las desviaciones que se presenten para garantizar la efectividad del proceso.</li>\r\n<li>Garantizar la confiabilidad de inventario del stock almacenado, de los insumos y/o materiales requeridos; solicitando reportes de inventario peri&oacute;dicos para disminuir gradualmente las diferencias de inventario y control de costos.</li>\r\n<li>Garantizar que el &Aacute;rea de mantenimiento lleve a cabo el programa de mantenimiento preventivo y correctivo de los equipos e instalaciones del almac&eacute;n, solicitando los reportes correspondientes para asegurar el funcionamiento eficiente y &oacute;ptimo de los equipos.</li>\r\n</ul>\r\n<p><em><span style=\"background-color: #00ccff;\">Ofrecemos:</span></em></p>\r\n<ul>\r\n<li>Sueldo competitivo</li>\r\n<li>Prestaciones de ley</li>\r\n<li>Prestaciones Superiores: Seguro de Vida, Seguro de Gastos M&eacute;dicos Mayores, Fondo y Caja de Ahorro, comedor</li>\r\n<li>Zona para laborar en Tlalpan, se requiere disponibilidad de horario</li>\r\n</ul>\r\n<p><span style=\"background-color: #00ccff;\"><em>Requerimientos:</em></span></p>\r\n<ul>\r\n<li>Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura</li>\r\n<li>A&ntilde;os de experiencia: 3</li>\r\n<li>Edad: entre 31 y 48 a&ntilde;os</li>\r\n<li>Disponibilidad de viajar: No</li>\r\n<li>Disponibilidad de cambio de residencia: No</li>\r\n</ul>\r\n<p><span style=\"background-color: #00ccff;\"><em>Funciones:</em></span></p>\r\n<p>&bull; Entradas y salidas de material <br />&bull; Conteos c&iacute;clicos <br />&bull; Acomodo de material y Surtido de pedidos <br />&bull; Manejo de inventarios <br /><br />Competencias/Habilidades<br />Din&aacute;mico, comunicaci&oacute;n efectiva, responsable, trabajo en equipo.</p>',7,7);
INSERT INTO `Offers` VALUES (6,'Analista de Crédito y Cobranza','Importante Empresa solicita Analista de crédito para actualizar y depurar las cuentas: integración documental y actualización en SAP.','2019-05-07',21000,'Active','<p><span style=\"color: #ff0000;\"><strong>Analista de Cr&eacute;dito y Cobranza</strong></span><br /><br /><strong>Requisitos:</strong></p>\r\n<ol>\r\n<li>Titulado. Licenciatura en Contabilidad, Administraci&oacute;n o af&iacute;n.</li>\r\n<li>3 a&ntilde;os en cr&eacute;dito y cobranza, manejo de Excel a nivel avanzado, SAP (FBL5N)</li>\r\n<li>Deseable experiencia en sector farmac&eacute;utico</li>\r\n</ol>\r\n<p><strong>Actividades:</strong></p>\r\n<ul>\r\n<li>Aplicaci&oacute;n de pagos, compensaci&oacute;n de notas de cr&eacute;dito.</li>\r\n<li>An&aacute;lisis y actualizaci&oacute;n en FBL5N de cada cuenta.</li>\r\n<li>Generaci&oacute;n de reportes para las diferentes &aacute;reas; comercial, operaciones, contralor&iacute;a, financiera, etc.</li>\r\n<li>Elaboraci&oacute;n del presupuesto de ingresos por cobranza.</li>\r\n</ul>\r\n<p><strong>Ofrecemos:</strong></p>\r\n<ul>\r\n<li>Sueldo competitivo</li>\r\n<li>Prestaciones de ley</li>\r\n<li>Prestaciones Superiores: Seguro de Vida, Seguro de Gastos M&eacute;dicos Mayores, Fondo y Caja de Ahorro.</li>\r\n<li>Comedor</li>\r\n<li>Zona para laborar, Tlalpan de lunes a viernes</li>\r\n</ul>\r\n<p><span style=\"color: #0000ff;\">Interesados que cubran el perfil al 100% postularse por este medio.</span><br /><br /><span style=\"color: #ff0000;\"><strong>Requerimientos</strong></span></p>\r\n<ol>\r\n<li>Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura</li>\r\n<li>A&ntilde;os de experiencia: 3</li>\r\n<li>Edad: entre 28 y 46 a&ntilde;os</li>\r\n<li>Conocimientos: Microsoft Excel, SAP</li>\r\n<li>Disponibilidad de viajar: No</li>\r\n<li>Disponibilidad de cambio de residencia: No</li>\r\n</ol>',2,2);
INSERT INTO `Offers` VALUES (7,'Diseñador grafico - Técnico o superior','Estamos solicitando personal, \"diseñador\" para sus puntos de venta localizados en la zona, debe tener conocimientos en alguno de los diferentes softwares de diseño, Corel, Photoshop o Illustrator.','2019-05-07',7200,'Active','<p><strong><span style=\"color: #0000ff;\">DESCRIPCION</span></strong></p>\r\n<p><span style=\"color: #ff0000;\"><em><strong>Consultor&iacute;a de Imagen est&aacute; en b&uacute;squeda del talento de un Dise&ntilde;ador Gr&aacute;fico</strong></em></span><br /><br /><span style=\"color: #0000ff;\"><em><strong>REQUISITOS:</strong></em></span><br /><br /><span style=\"color: #000000;\">- Licenciatura en Dise&ntilde;o</span><br /><span style=\"color: #000000;\">- Experiencia m&iacute;nima de dos a&ntilde;os desarrollando actividades de dise&ntilde;o publicitario, y de identidad corporativa.</span><br /><span style=\"color: #000000;\">- Manejo de paqueter&iacute;a: Photo shop, Illustrator, Adobe InDesign, Dreamweaver, HTML</span><br /><span style=\"color: #000000;\">- Desarrollo de mensajes visuales, informativos, de identidad y de persuasi&oacute;n</span><br /><span style=\"color: #000000;\">- Estrategia digital, uso de medios digitales audio, v&iacute;deo e imagen</span></p>\r\n<p><span style=\"color: #0000ff;\"><em><strong>FUNCIONES:</strong></em></span></p>\r\n<p><span style=\"color: #000000;\">- Dise&ntilde;ar papeler&iacute;a institucional, estrategias digitales, presentaciones corporativas. Crear y editar fotograf&iacute;as y gr&aacute;ficos - Preparar archivos para impresi&oacute;n digital y offset</span><br /><span style=\"color: #000000;\">- Dise&ntilde;o, construcci&oacute;n y mantenimiento de p&aacute;ginas web, as&iacute; como campa&ntilde;as de mailing-newsletter etc.</span><br /><span style=\"color: #000000;\">- Dise&ntilde;o de material gr&aacute;fico para redes sociales y p&aacute;ginas web</span><br /><span style=\"color: #000000;\">- Desarrollo y dise&ntilde;o de eventos como ferias, exposiciones, festejos, aniversarios etc.</span><br /><br /><span style=\"color: #0000ff;\"><em><strong>OFRECEMOS:</strong></em></span></p>\r\n<p><span style=\"color: #000000;\">- Pago semanal</span><br /><span style=\"color: #000000;\">- Prestaciones de Ley</span><br /><span style=\"color: #000000;\">- Contrataci&oacute;n directa por la empresa Horario 9:00 AM&ndash; 7:00 PM de Lunes a Viernes</span></p>\r\n<p><span style=\"color: #0000ff;\"><em><strong>REQUERIMIENTOS:</strong></em></span></p>\r\n<p><span style=\"color: #000000;\">- Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura</span><br /><span style=\"color: #000000;\">- A&ntilde;os de experiencia: 2</span><br /><span style=\"color: #000000;\">- Edad: entre 24 y 40 a&ntilde;os</span><br /><span style=\"color: #000000;\">- Conocimientos: Microsoft Office</span><br /><span style=\"color: #000000;\">- Disponibilidad de viajar: No</span><br /><span style=\"color: #000000;\">- Disponibilidad de cambio de residencia: No</span></p>',9,4);
INSERT INTO `Offers` VALUES (8,'Gerente de Recursos Humanos','Reclutamos para una empresa líder de transporte especializado un Gerente de Recursos Humanos con buen liderazgo, dinámico, negociador, pro activo, enfocado a resultados.','2019-05-08',22000,'Deleted','<p><span style=\"color: #ff0000;\"><strong>Funciones:</strong></span></p>\r\n<ul>\r\n<li>Responsable de las &aacute;reas de Reclutamiento, Capcitaci&oacute;n, N&oacute;mina, Laboral, Compensaciones, Seguridad Patrimonial.</li>\r\n<li>Promover la cultura organizacional</li>\r\n<li>Asegurar el cumplimiento de pol&iacute;ticas, normas y procedimientos.</li>\r\n<li>Desarrollar el talento.</li>\r\n<li>Asegurar el reclutamiento en tiempo y forma, cuidando las pol&iacute;ticas establecidas.</li>\r\n<li>Garantizar la equidad interna, competitividad externa y pago oportuno.</li>\r\n<li>Asegurar el cumplimiento del Reglamento Interior de Trabajo.</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Requisitos:</strong></span></p>\r\n<ul>\r\n<li>Carrera profesional (LAE, LRH, af&iacute;n)</li>\r\n<li>5 a&ntilde;os de experiencia como Gerente de Recursos Humanos</li>\r\n<li>Experiencia en empresas de transporte</li>\r\n<li>Conocimiento en esquemas de desarrollo, compensaciones, reclutamiento.</li>\r\n<li>Preferentemente con buen nivel de ingl&eacute;s</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Se ofrece:</strong></span></p>\r\n<ul>\r\n<li>Sueldo competitivo</li>\r\n<li>Prestaciones de ley</li>\r\n<li>Bonos de despensa</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Requerimientos</strong></span></p>\r\n<ul>\r\n<li>Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura</li>\r\n<li>A&ntilde;os de experiencia: 5</li>\r\n<li>Idiomas: Ingl&eacute;s</li>\r\n<li>Edad: entre 30 y 50 a&ntilde;os</li>\r\n<li>Conocimientos: Microsoft Office</li>\r\n<li>Disponibilidad de viajar: Si</li>\r\n<li>Disponibilidad de cambio de residencia: No</li>\r\n</ul>\r\n<p><span style=\"color: #0000ff;\"><strong>Interesados enviar su CV por este medio.</strong></span></p>',5,5);
INSERT INTO `Offers` VALUES (9,'Residente de obra Arquitecto o Ing Civil','Estamos solicitando Arquitecto / Ing Civil con experiencia en desarrollos habitacionales, en los procesos de venta, comercialización, publicidad y administradores de obras.','2019-05-08',16500,'Active','<p><strong>Descripci&oacute;n</strong><br />Empresa constructora solicita personal para puesto de Residente de Obra.<br /><br /><strong>Escolaridad:</strong> Arquitecto y/o Ingeniero Civil<br /><br /><strong>Funci&oacute;n principal:</strong><br />* Supervisi&oacute;n y control de obra<br />* Cuantificacion de avances<br />* Elaboraci&oacute;n de estimaciones<br />* Manejo de contratistas y personal<br />* Estimaciones de avance de obra<br />* Elaboraci&oacute;n de paquetes de obra<br />* Realizaciones de reports de obra<br />* Interpretaci&oacute;n de planos.( Memorias de calculo ..)<br />* An&aacute;lisis de precios unitarios.<br />* Ejecuci&oacute;n de proyectos.<br />* Bit&aacute;cora de obra<br /><br /><strong>Habilidades:</strong><br />* Negociaci&oacute;n, liderazgo, capacidad para resolver conflictos<br />* Opus, AutoCAD, Excell<br /><br /><strong>Requerimientos</strong><br />* Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura<br />* A&ntilde;os de experiencia: 4<br />* Idiomas: Ingl&eacute;s<br />* Edad: entre 27 y 60 a&ntilde;os<br />* Disponibilidad de viajar: No<br />* Disponibilidad de cambio de residencia: No</p>\r\n<p><span style=\"background-color: #ffffff; color: #0000ff;\"><strong>Interesados, favor en enviar su CV en formato PDF aqu&iacute; en la plataforma.</strong></span></p>',1,6);
INSERT INTO `Offers` VALUES (10,'Administrador de redes y Sistema Operativo Red Hat','Empresa importante del sector financiero solicita Ing. de Sistemas para monitorear y administrar el desempeño de los servidores Linux.','2019-05-09',25000,'Active','<p style=\"text-align: center;\"><span style=\"color: #0000ff;\">&iexcl;&iexcl;INT&Eacute;GRATE A NUESTRO GRAN EQUIPO!!</span><br /><span style=\"color: #0000ff;\">EMPRESA IMPORTANTE SOLICITA ADMINISTRADOR DE SO RED HAT ENTERPRISE LINUX.</span></p>\r\n<p><span style=\"color: #ff0000;\"><strong>Requisitos:</strong></span></p>\r\n<ul>\r\n<li>Ingeniero en computaci&oacute;n o carrera af&iacute;n (Titulado)</li>\r\n<li>Promedio m&iacute;nimo de 8.0</li>\r\n<li>Sexo: Indistinto</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Experiencia m&iacute;nima de 2 a 3 a&ntilde;os en:</strong></span></p>\r\n<ul>\r\n<li>Instalaci&oacute;n, configuraci&oacute;n, aplicaci&oacute;n de parches y actualizaci&oacute;n del software mencionado.</li>\r\n<li>Implementaci&oacute;n de recomendaciones de seguridad inform&aacute;tica y buenas pr&aacute;cticas.</li>\r\n<li>Resoluci&oacute;n de problemas a nivel Sistema Operativo.</li>\r\n<li>Monitoreo y an&aacute;lisis de desempe&ntilde;o de los servidores Linux.</li>\r\n<li>Atenci&oacute;n de reportes de Soporte t&eacute;cnico, atenci&oacute;n y asesor&iacute;as a usuarios finales asignados por el Centro de Soporte Institucional (Mesa de Ayuda).</li>\r\n<li>Alta, baja y modificaci&oacute;n de cuentas de usuario.</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Contar con alguno de los siguientes cursos:</strong></span></p>\r\n<ul>\r\n<li>Red Hat System Administration I version 6 o superior</li>\r\n<li>Red Hat System Administration II</li>\r\n<li>Certificaci&oacute;n Red Hat Certified System Administrator (RHCSA) para versi&oacute;n 6 o superior</li>\r\n<li>Red Hat Certified Engineer (RHCE) para versi&oacute;n 6 o superior</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Propuesta de trabajo:</strong></span></p>\r\n<ul>\r\n<li>Sueldo seg&uacute;n experiencia y conocimientos</li>\r\n<li>Prestaciones de Ley</li>\r\n<li>Zona de trabajo: Legar&iacute;a</li>\r\n<li>Horario propuesto: lunes a viernes de 9 &ndash; 18 horas</li>\r\n</ul>\r\n<p><span style=\"color: #ff0000;\"><strong>Requerimientos</strong></span></p>\r\n<ol>\r\n<li>Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura</li>\r\n<li>A&ntilde;os de experiencia: 2</li>\r\n<li>Disponibilidad de viajar: No</li>\r\n<li>Disponibilidad de cambio de residencia: No</li>\r\n</ol>\r\n<p><span style=\"color: #008000;\"><strong>SI CUMPLES CON LOS REQUISITOS MENCIONADOS NO DUDES EN ENVIARNOS TU CV POR ESTE MEDIO.</strong> </span></p>',3,7);
INSERT INTO `Offers` VALUES (11,'Programador de Software','Empresa con presencia a nivel nacional, te invita a formar parte de su equipo de trabajo para diseñar, crear y realizar mantenimiento a páginas y aplicaciones web.','2019-05-09',19700,'Created','<p><span style=\"color: #ff0000;\"><strong>PROGRAMADOR DE SOFTWARE</strong></span><br /><br /><strong>OBJETIVO:</strong> DISE&Ntilde;AR, CREAR Y REALIZAR MANTENIMIENTO A P&Aacute;GINAS Y APLICACIONES WEB<br /><br /><strong><span style=\"color: #0000ff;\">REQUISITOS:</span></strong></p>\r\n<p>* EDAD: 25 A 35 A&Ntilde;OS<br />* LICENCIATURA: DESARROLLO DE SOFTWARE, INFORM&Aacute;TICA, INGENIER&Iacute;A EN SISTEMAS COMPUTACIONALES O AF&Iacute;N<br />* INGLES T&Eacute;CNICO<br />* EXPERIENCIA EN PUESTO SIMILAR M&Iacute;NIMA 3 A&Ntilde;OS (ESTAR EJERCIENDO ACTUALMENTE)<br />* MANEJO DE ERP<br />* EXPERIENCIA EN VISUAL C# (2 A&Ntilde;OS)<br />* EXPERIENCIA EN JAVA (2 A&Ntilde;OS)<br />* EXPERIENCIA EN MICROSOFT SQL SERVER (2 A&Ntilde;OS)<br />* EXPERIENCIA DE MVC Y POO (1 A&Ntilde;O)<br /><br /><strong><span style=\"color: #0000ff;\">FUNCIONES: (EXPERIENCIA EN ESTAS ACTIVIDADES)</span></strong></p>\r\n<p>* MANEJO DE C&Oacute;DIGO EN DISTINTOS LENGUAJES DE PROGRAMACI&Oacute;N, TALES COMO HTML, XML PHP Y JAVASCRIPT.<br />* DISE&Ntilde;AR NUEVAS APLICACIONES Y SITIOS DE INTERNET<br />* DETECTAR Y SOLUCIONAR ERRORES O PROBLEMAS EN LA EJECUCI&Oacute;N DE LAS APLICACIONES Y SITIOS WEB.<br />* AGREGAR NUEVAS FUNCIONES A LA EJECUCI&Oacute;N DE LOS SITIOS DE INTERNET Y LAS APLICACIONES<br />* ESTAR AL CORRIENTE CON LOS NUEVOS LENGUAJES DE PROGRAMACI&Oacute;N, TECNOLOG&Iacute;AS Y TENDENCIAS EN EL MERCADO<br />* COLABORAR EN LA ATENCI&Oacute;N Y CAPACITACI&Oacute;N DE LOS USUARIOS EN LOS SISTEMAS IMPLEMENTADOS<br />* DOCUMENTAR ADECUADAMENTE LOS PROGRAMAS DESARROLLADOS<br /><br /><strong><span style=\"color: #0000ff;\">OFERTA:</span></strong></p>\r\n<p>* PERCEPCI&Oacute;N MENSUAL $10,000 NETOS (PAGO QUINCENAL, INCLUYE VALES DE DESPENSA)<br />* PRESTACIONES DE LEY<br />* HORARIO: L-V 9:00 A 7:00 Y S&Aacute;BADO MEDIO D&Iacute;A<br /><br /><span style=\"color: #0000ff;\"><strong>REQUERIMIENTOS</strong></span></p>\r\n<p>* EDUCACI&Oacute;N M&Iacute;NIMA: EDUCACI&Oacute;N SUPERIOR - LICENCIATURA<br />* A&Ntilde;OS DE EXPERIENCIA: 3<br />* IDIOMAS: INGL&Eacute;S<br />* EDAD: ENTRE 25 Y 35 A&Ntilde;OS<br />* CONOCIMIENTOS: SQL SERVER, C#, JAVA, ASP.NET MVC, MYSQL<br />* DISPONIBILIDAD DE VIAJAR: NO<br />* DISPONIBILIDAD DE CAMBIO DE RESIDENCIA: NO</p>\r\n<p><em><strong>SI CUMPLES CON EL PERFIL. POR FAVOR ENVIANOS TU CV POR ESTE MEDIO EN FORMATO PDF, DOCX.</strong></em></p>\r\n<p>&nbsp;</p>',8,2);
INSERT INTO `Offers` VALUES (12,'Ejecutivo Contable','Empresa internacional solicita Contador Público para realizar las siguientes actividades: conciliaciones y movimientos bancarios, emisión de estados financieros, cálculo de impuestos y presentación de declaraciones, estrategias fiscales, entre otros.','2019-05-09',16900,'Active','<p><strong>EMPRESA L&Iacute;DER EN LA ADMINISTRACI&Oacute;N DE CAPITAL HUMANO EST&Aacute; EN B&Uacute;SQUEDA DE:</strong><br /><br /><strong>EJECUTIVO DE CONTABLE</strong><br /><br /><strong><span style=\"color: #ff0000;\">REQUISITOS:</span></strong><br /><br />- Contabilidad (Titulado o Pasante)<br />- 1 a&ntilde;o de experiencia comprobable en el &aacute;rea contable.<br /><br /><span style=\"color: #ff0000;\"><strong>FUNCIONES:</strong></span><br /><br />- Manejar los registros contables (p&oacute;lizas de ingreso y egresos diarios).<br />- C&aacute;lculo de impuestos provisionales.<br />- C&aacute;lculo de declaraciones anuales para personas morales y f&iacute;sicas.<br />- Elaboraci&oacute;n de estados financieros.<br />- Atenci&oacute;n a visitas domiciliarias.<br /><br /><strong>ZONA DE TRABAJO: Col. Buena Vista, D.F</strong></p>\r\n<p><span style=\"color: #ff0000;\"><strong>OFRECEMOS:</strong></span></p>\r\n<p>- Salario atractivo, seg&uacute;n experiencia.<br />- Prestaciones superiores a las de ley (seguro de vida, seguro de gastos m&eacute;dicos, seguro de gastos funerarios, tarjeta de descuentos, universidad corporativa), desde el primer d&iacute;a.<br /><br /><span style=\"color: #ff0000;\"><strong>REQUERIMIENTOS:</strong></span></p>\r\n<p>- Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura<br />- A&ntilde;os de experiencia: 1<br />- Disponibilidad de viajar: Si<br />- Disponibilidad de cambio de residencia: No</p>\r\n<p>INTERESADOS FAVOR DE ENVIAR SU CV POR ESTE MEDIO.<br /><br /><strong>SOMOS UNA EMPRESA INCLUYENTE QUE RESPETA LA DIVERSIDAD Y NO HACE NING&Uacute;N TIPO DE DISCRIMINACI&Oacute;N YA SEA POR G&Eacute;NERO, DISCAPACIDAD, ORIENTACI&Oacute;N POL&Iacute;TICA, RELIGIOSA O SEXUAL, NI CONDICI&Oacute;N SOCIAL O EDAD.</strong></p>',2,4);
INSERT INTO `Offers` VALUES (13,'Ingeniero electrónico','Estamos reclutando Ing. Electrónico para diseñar equipos y sistemas electrónicos (circuitos), tanto analógicos como digitales y para dirigir la operación de equipos y sistemas electrónicos en las áreas de comunicación, control, instrumentación y potencia.','2019-05-09',23600,'Created','<p>&iexcl;&Uacute;nete a nuestro equipo de trabajo!<br /><br />Nuestra visi&oacute;n es ser la inspiraci&oacute;n de la industria de la entrega inmediata, a trav&eacute;s del desarrollo del talento humano y la excelencia operacional.</p>\r\n<p><span style=\"color: #0000ff;\"><strong>FUNCIONES:</strong></span><br />- Mantendr&aacute; los equipos, maquinaria dentro del sectro en excelentes condiciones mediante un buen servicio de mantenimiento preventivo, correctivo, predictivo.<br />- Evitar&aacute; paros inoportunos y p&eacute;rdidas para la empresa.<br />- Apoyar&aacute; a producci&oacute;n medienta capacitaci&oacute;n de maquinaria nueva.<br />- Coordinar&aacute; al personal para establecer par&aacute;metros en &aacute;rea.<br /><br /><span style=\"color: #0000ff;\"><strong>DEBERAS TENER:</strong></span><br />- Ing. en mecatr&oacute;nica, electr&oacute;nica y electromec&aacute;nico.<br />- Experiencia en el ramo de mantenimiento en general.<br />- Conocimiento en maquinaria industrial.<br />- 2 a&ntilde;os de experiencia minimo.<br />- Destrezay habilidad en herramientas mec&aacute;nicas, el&eacute;ctricas y electr&oacute;nicas.<br /><br /><span style=\"color: #0000ff;\"><strong>VALORAMOS:</strong></span><br />- Liderazgo<br />- Responsabilidad<br />- Actitud de servicio<br />- Enfoque a resultados<br />- Comunicaci&oacute;n efectiva<br />- Retenci&oacute;n de informaci&oacute;n<br />- Habilidad anal&iacute;tica<br /><br /><span style=\"color: #0000ff;\"><strong>QUE OFRECEMOS:</strong></span><br />- Prestaciones superiores a la ley (fondo de ahorro, 30 d&iacute;as de aguinaldo, 15 d&iacute;as de vacaciones, vales de despensa, utilidades, bono por puntualidad y asistencia, SGMM).<br />- Estabilidad y crecimiento laboral.</p>\r\n<p><span style=\"color: #0000ff;\"><strong>REQUERIMIENTOS:</strong></span><br />- Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura<br />- A&ntilde;os de experiencia: 3<br />- Edad: entre 25 y 30 a&ntilde;os<br />- Conocimientos: Windows, Administraci&oacute;n de archivos<br />- Disponibilidad de viajar: No<br />- Disponibilidad de cambio de residencia: No</p>\r\n<p><span style=\"color: #0000ff;\"><strong>SI CUBRES EL PERFIL, POR FAVOR ENVIANOS TU CV POR ESTE MEDIO.</strong></span></p>',4,5);

/*Applications*/
DROP TABLE IF EXISTS `applications`;
CREATE TABLE `applications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `comments` text,
  `offer_id` int NOT NULL,
  `user_id` int NOT NULL,
  `status` ENUM('ACCEPTED','REJECTED','UNKNOWN') DEFAULT 'UNKNOWN',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Offer_User_UNIQUE` (`offer_id`,`user_id`),
  KEY `fk_Applications_Offers1_idx` (`offer_id`),
  KEY `fk_Applications_Users1_idx` (`user_id`),
  CONSTRAINT `fk_Applications_Users1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `fk_Applications_Offers1` FOREIGN KEY (`offer_id`) REFERENCES `Offers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Authorities*/
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
	`user_id` int NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
	KEY `fk_Roles_Users1_idx` (`user_id`),
	CONSTRAINT `fk_Roles_Users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
	KEY `fk_Users_Roles1_idx` (`role_id`),
	CONSTRAINT `fk_Users_Roles1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);
INSERT INTO `users_roles` VALUES(1,1);/*Admins*/
INSERT INTO `users_roles` VALUES(1,3);
INSERT INTO `users_roles` VALUES(2,2);
INSERT INTO `users_roles` VALUES(2,3);
INSERT INTO `users_roles` VALUES(4,2);/*Enterprises*/
INSERT INTO `users_roles` VALUES(5,2);
INSERT INTO `users_roles` VALUES(6,2);
INSERT INTO `users_roles` VALUES(7,2);
INSERT INTO `users_roles` VALUES(3,1);/*Users*/
INSERT INTO `users_roles` VALUES(8,1);
INSERT INTO `users_roles` VALUES(9,1);
INSERT INTO `users_roles` VALUES(10,1);
INSERT INTO `users_roles` VALUES(11,1);
INSERT INTO `users_roles` VALUES(12,1);