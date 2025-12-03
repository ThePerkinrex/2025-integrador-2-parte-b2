
package es.upm.grise.profundizacion.file;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.upm.grise.profundizacion.exceptions.EmptyBytesArrayException;
import es.upm.grise.profundizacion.exceptions.InvalidContentException;
import es.upm.grise.profundizacion.exceptions.WrongFileTypeException;

class FileTest {


	@InjectMocks
	private File file;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// --- addProperty() Tests ---

	@Test
	@DisplayName("addProperty: contenido válido y tipo PROPERTY → se añade correctamente")
	void testAddPropertyValidContent() throws Exception {
		file.setType(FileType.PROPERTY);
		char[] newContent = { 'K', '=', 'V' };

		file.addProperty(newContent);

		List<Character> content = file.getContent();
		assertEquals(3, content.size());
		assertIterableEquals(List.of('K', '=', 'V'), content);
	}

	@Test
	@DisplayName("addProperty: newcontent es null → lanza InvalidContentException")
	void testAddPropertyNullContent() {
		file.setType(FileType.PROPERTY);
		assertThrows(InvalidContentException.class, () -> file.addProperty(null));
	}

	@Test
	@DisplayName("addProperty: tipo IMAGE → lanza WrongFileTypeException")
	void testAddPropertyWrongType() {
		file.setType(FileType.IMAGE);
		char[] newContent = { 'A', '=', 'B' };
		assertThrows(WrongFileTypeException.class, () -> file.addProperty(newContent));
	}

	// --- getCRC32() Tests ---

	@Test
	@DisplayName("getCRC32: contenido vacío → devuelve 0")
	void testGetCRC32EmptyContent() throws Exception {
		file.setType(FileType.PROPERTY);
		assertEquals(0, file.getCRC32());
	}


	// --- setType() Test ---
	@Test
	@DisplayName("setType: establece correctamente el tipo")
	void testSetType() {
		file.setType(FileType.IMAGE);
		assertThrows(WrongFileTypeException.class, () -> file.addProperty(new char[] { 'A' }));
	}

	// --- getContent() Test ---
	@Test
	@DisplayName("getContent: devuelve contenido actualizado")
	void testGetContent() throws Exception {
		file.setType(FileType.PROPERTY);
		file.addProperty(new char[] { 'D', '=', '1' });

		List<Character> content = file.getContent();
		assertIterableEquals(List.of('D', '=', '1'), content);
	}
}
