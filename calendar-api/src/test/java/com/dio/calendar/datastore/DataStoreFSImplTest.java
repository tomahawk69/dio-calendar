package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by iovchynnikov on 5/21/14.
 */
public class DataStoreFSImplTest {
    private DataStoreFSImpl dataStore;
    String path = "db_test";

    @Before
    public void setUp() throws Exception {
        dataStore = spy(new DataStoreFSImpl(path, new FileSystemHelperImpl()));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWriteFileExists() throws Exception {
        // initialize variable inputs
        String dbPath = "db_test";
        Entry inputEntry = new Entry.Builder().subject("test").attenders("1@1.com", "2@2.com").build();
        EntryWrapper inputWrapper = new EntryWrapper(inputEntry);


        // initialize mocks
        Path mockDbPath = mock(Path.class);
        Path mockFilePath = mock(Path.class);
        Marshaller mockMarshaller = mock(Marshaller.class);
        FileSystemHelper helperMock = mock(FileSystemHelper.class);
        File mockFile = mock(File.class);

        when(helperMock.getPath(dbPath)).thenReturn(mockDbPath);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(dbPath, helperMock));
        when(helperMock.getPath(inputEntry.getId(), mockDbPath)).thenReturn(mockFilePath);
        when(testClass.createMarschaller(EntryWrapper.class)).thenReturn(mockMarshaller);
        when(mockFilePath.toFile()).thenReturn(mockFile);
        when(helperMock.getEntryWrapper(inputEntry)).thenReturn(inputWrapper);
        //doNothing().when(mockMarshaller).marshal(any(EntryWrapper.class), mockFile);

        // initialize class to test

        // invoke method on class to test
        Boolean resultReceived = testClass.write(inputEntry);

        // assert return value
        assertTrue(resultReceived);

        // verify mock expectations
        verify(mockMarshaller).marshal(inputWrapper, mockFile);
        verify(helperMock).getPath(dbPath);
        verify(helperMock).getPath(inputEntry.getId(), mockDbPath);

    }

    @Test
    public void testDeleteUuid() throws Exception {
        // initialize variable inputs
        String dbPath = "/";
        UUID inputId = UUID.randomUUID();

        // initialize mocks
        Path mockFilePath = mock(Path.class);
        Path mockDbPath = mock(Path.class);
        FileSystemHelper helperMock = mock(FileSystemHelper.class);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(dbPath, helperMock));
        when(helperMock.getPath(dbPath)).thenReturn(mockDbPath);
        when(helperMock.getPath(inputId, mockDbPath)).thenReturn(mockFilePath);
        when(helperMock.delete(mockFilePath)).thenReturn(true);

        // initialize class to test

        // invoke method on class to test
        testClass.delete(inputId);

        // assert return value

        // verify mock expectations
    }

    @Test
    public void testDeleteFile() throws Exception {
        // initialize variable inputs
        String dbPath = "/";

        // initialize mocks
        Path mockFilePath = mock(Path.class);
        Path mockDbPath = mock(Path.class);
        FileSystemHelper helperMock = mock(FileSystemHelper.class);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(dbPath, helperMock));
        when(helperMock.getPath(dbPath)).thenReturn(mockDbPath);
        when(helperMock.delete(mockFilePath)).thenReturn(true);

        // initialize class to test

        // invoke method on class to test
        testClass.delete(mockFilePath);

        // assert return value

        // verify mock expectations
    }

    @Test
    public void testReadUuid() throws Exception {
        // initialize variable inputs
        Entry inputEntry = new Entry.Builder().subject("test").attenders("1@1.com","2@2.com").build();
        String mockPath = "mockPath";

        // initialize mocks
        Path entryPath = mock(Path.class);

        Path systemPath = mock(Path.class);
        FileSystemHelper helper = mock(FileSystemHelper.class);

        when(helper.getPath(mockPath)).thenReturn( systemPath);
        when(helper.getPath(inputEntry.getId(), systemPath)).thenReturn(entryPath);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(mockPath, helper));
        doReturn(inputEntry).when(testClass).read(entryPath);

        // initialize class to test
        // invoke method on class to test
        Entry resultEntry = testClass.read(inputEntry.getId());

        // assert return value
        Assert.assertEquals(inputEntry, resultEntry);

        // verify mock expectations
    }

    @Test
    public void testReadFile() throws Exception {
        // initialize variable inputs
        Entry inputEntry = new Entry.Builder().subject("test").attenders("1@1.com","2@2.com").build();
        EntryWrapper expectedEntryWrapper = new EntryWrapper(inputEntry);
        String mockPath = "mockPath";

        // initialize mocks
        File file = mock(File.class);
        Path entryPath = mock(Path.class);
        when(entryPath.toFile()).thenReturn(file);

        Path systemPath = mock(Path.class);

        FileSystemHelper helper = mock(FileSystemHelper.class);
        when(helper.getPath(mockPath)).thenReturn( systemPath);
        when(helper.getPath(inputEntry.getId(), systemPath)).thenReturn(entryPath);
        when(helper.exists(entryPath)).thenReturn(true);

        Unmarshaller unmarshaller = mock(Unmarshaller.class);
        when(unmarshaller.unmarshal(file)).thenReturn(expectedEntryWrapper);


        // initialize class to test
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(mockPath,helper));
        when(testClass.createUnmarschaller(EntryWrapper.class)).thenReturn(unmarshaller);

        // invoke method on class to test
//        dataStore.write(inputEntry);
        Entry resultEntry = testClass.read(inputEntry.getId());

        // assert return value
        Assert.assertEquals(inputEntry, resultEntry);

        // verify mock expectations
        verify(testClass).createUnmarschaller(EntryWrapper.class);
    }

    @Test
    public void testReadNull() throws Exception {
        // initialize variable inputs
        UUID inputId = UUID.randomUUID();
        String dbPath = "/";

        // initialize mocks
        Path mockDbPath = mock(Path.class);
        Path mockFilePath = mock(Path.class);
        FileSystemHelper helperMock = mock(FileSystemHelperImpl.class);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(dbPath, helperMock));

        when(helperMock.getPath(dbPath)).thenReturn(mockDbPath);
        when(helperMock.getPath(inputId, mockDbPath)).thenReturn(mockFilePath);
        when(helperMock.exists(mockFilePath)).thenReturn(false);

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = testClass.read(inputId);


        // assert return value
        Assert.assertNull(resultEntry);

        // verify mock expectations
        verify(dataStore, times(0)).createUnmarschaller(EntryWrapper.class);
    }

    @Test
    public void testClear() throws Exception {
        String dbPath = "db_test";

        // initialize mocks
        Path mockFile1 = mock(Path.class);
        Path mockFile2 = mock(Path.class);
        Path mockDbPath = mock(Path.class);
        List<Path> listMockFiles = new ArrayList<>(Arrays.asList(mockFile1, mockFile2));

        FileSystemHelper helperMock = mock(FileSystemHelperImpl.class);
        when(helperMock.getPath(dbPath)).thenReturn(mockDbPath);
        DataStoreFSImpl testClass = spy(new DataStoreFSImpl(dbPath, helperMock));

        when(helperMock.getListFiles(mockDbPath)).thenReturn(listMockFiles);
        when(helperMock.delete(mockFile1)).thenReturn(true);
        when(helperMock.delete(mockFile2)).thenReturn(true);

        // initialize mocks
        // initialize class to test
        testClass.clear();

        // invoke method on class to test

        // assert return value

        // verify mock expectations\
    }

}
