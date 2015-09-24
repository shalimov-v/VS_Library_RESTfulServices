-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.5.23 - MySQL Community Server (GPL)
-- ОС Сервера:                   Win64
-- HeidiSQL Версия:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Дамп данных таблицы test.author: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` (`id`, `firstName`, `lastName`) VALUES
	(1, 'Anton', 'Chekhov'),
	(2, 'Ivan', 'Bunin'),
	(3, 'Mikhail', 'Saltykov-Schedrin'),
	(4, 'Alexandr', 'Pushkin'),
	(5, 'Alexandr', 'Blok'),
	(6, 'Mikhail', 'Lermontov'),
	(7, 'Fedor', 'Dostoevskiy');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;

-- Дамп данных таблицы test.book: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`id`, `name`, `isbn`, `authorid`) VALUES
	(1, 'Uncle Vanya', '978-5-4241-2615-4', 1),
	(2, 'Three Sisters', '978-5-389-01216-5', 1),
	(3, 'Light Breath', '978-5-699-40898-6', 2),
	(5, 'Fairy Tales', '978-5-389-09193-1', 3);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
