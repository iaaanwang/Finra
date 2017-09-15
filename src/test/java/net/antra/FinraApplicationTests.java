package net.antra;

import net.antra.controller.FileUploadController;
import net.antra.dao.FileDao;
import net.antra.entity.MyFile;
import net.antra.exception.FileUploadException;
import net.antra.service.FileService;
import net.antra.service.FileServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinraApplicationTests {

	@Mock
	FileDao fileDAO;
	@InjectMocks
	FileServiceImp fileService;
	@Mock
	FileService fileServiceMock;
	@InjectMocks
	FileUploadController controller;

	private MockMvc mockMvc;
	private MyFile file ;
	private MultipartFile mpFile;
	private List<MyFile> testRes = new ArrayList<>();


	@Before
	public void initTest() {
		file=new MyFile("fakeName",new Date(),100,"fakaPath");
		mpFile=new MockMultipartFile("fakeName","nonsensecontent".getBytes());
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}


	@Test
	public void testGetFile() {

		when(fileDAO.getFile(file.getId())).thenReturn(file);
		assertEquals(fileService.getFile(file.getId()).hashCode(),file.hashCode());
		assertEquals(fileService.getFile(file.getId()).toString(),file.toString());

	}

	@Test
	public void testSaveFile() throws IOException{
		when(fileDAO.save(file)).thenReturn(file);
		assertEquals(fileService.saveFile(mpFile,file).hashCode(),file.hashCode());
		assertEquals(fileService.saveFile(mpFile,file).toString(),file.toString());
	}


	@Test
	public void testGetAll() {
		testRes.add(file);
		when(fileDAO.getAll()).thenReturn(testRes);
		assertTrue(fileService.getAllFiles().size() == 1 && fileService.getAllFiles().get(0).toString().equals(testRes.get(0).toString()));

	}
	@Test(expected=FileUploadException.class)
	public void testGetFileWithException() throws Exception{
		when(fileServiceMock.getFile(100)).thenReturn(null);
		controller.getFileInfo(100);
	}
}
