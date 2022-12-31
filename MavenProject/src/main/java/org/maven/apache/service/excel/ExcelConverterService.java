package org.maven.apache.service.excel;

import java.io.File;
import java.io.IOException;

public interface ExcelConverterService {

    void convertToExcel(File file) throws IOException;
}
