package main.java;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.Page;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.page.PageType;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Params;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.File;
import java.io.IOException;
/**
 * Created by NAVER on 2017-07-06.
 */
public class HtmltoPdf {

    private static final String STDINOUT = "-";

    private final WrapperConfig wrapperConfig;

    private final Params params;

    private final List<Page> pages;

    private boolean hasToc = false;

    public HtmltoPdf() {
        this(new WrapperConfig());
    }

    public HtmltoPdf(WrapperConfig wrapperConfig) {
        this.wrapperConfig = wrapperConfig;
        this.params = new Params();
        this.pages = new ArrayList<Page>();
    }

    /**
     * Add a page to the pdf
     *
     * @deprecated Use the specific type method to a better semantic
     */
    @Deprecated
    public void addPage(String source, PageType type) {
        this.pages.add(new Page(source, type));
    }

    /**
     * Add a page from an URL to the pdf
     */
    public void addPageFromUrl(String source) {
        this.pages.add(new Page(source, PageType.url));
    }

    /**
     * Add a page from a HTML-based string to the pdf
     */
    public void addPageFromString(String source) {
        this.pages.add(new Page(source, PageType.htmlAsString));
    }

    /**
     * Add a page from a file to the pdf
     */
    public void addPageFromFile(String source) {
        this.pages.add(new Page(source, PageType.file));
    }

    public void addToc() {
        this.hasToc = true;
    }

    public void addParam(Param param, Param... params) {
        this.params.add(param, params);
    }

    public File saveAs(String path) throws IOException, InterruptedException {
        File file = new File(path);
        FileUtils.writeByteArrayToFile(file, getPDF());
        return file;
    }

    public byte[] getPDF() throws IOException, InterruptedException {

        try {
            Process process = Runtime.getRuntime().exec(getCommandAsArray());

            byte[] inputBytes = IOUtils.toByteArray(process.getInputStream());
            byte[] errorBytes = IOUtils.toByteArray(process.getErrorStream());

            process.waitFor();

            if (process.exitValue() != 0) {
                throw new RuntimeException("Process (" + getCommand() + ") exited with status code " + process.exitValue() + ":\n" + new String(errorBytes));
            }

            return inputBytes;
        } finally {
            cleanTempFiles();
        }
    }

    private String[] getCommandAsArray() throws IOException {
        List<String> commandLine = new ArrayList<String>();

        if (wrapperConfig.isXvfbEnabled())
            commandLine.addAll(wrapperConfig.getXvfbConfig().getCommandLine());

        commandLine.add(wrapperConfig.getWkhtmltopdfCommand());

        commandLine.addAll(params.getParamsAsStringList());

        if (hasToc)
            commandLine.add("toc");

        for (Page page : pages) {
            if (page.getType().equals(PageType.htmlAsString)) {

                File temp = File.createTempFile("java-wkhtmltopdf-wrapper" + UUID.randomUUID().toString(), ".html");
                FileUtils.writeStringToFile(temp, page.getSource(), "UTF-8");

                page.setSource(temp.getAbsolutePath());
            }

            commandLine.add(page.getSource());
        }
        commandLine.add(STDINOUT);
        return commandLine.toArray(new String[commandLine.size()]);
    }

    private void cleanTempFiles() {
        for (Page page : pages) {
            if (page.getType().equals(PageType.htmlAsString)) {
                new File(page.getSource()).delete();
            }
        }
    }

    public String getCommand() throws IOException {
        return StringUtils.join(getCommandAsArray(), " ");
    }

    public static void makeAPdf() throws InterruptedException, IOException {
        Process wkhtml; // Create uninitialized process
        String command = "wkhtmltopdf C:/Users/NAVER/Desktop/calendarSource.html C:/Users/NAVER/Desktop/calendar.pdf"; // Desired command

        wkhtml = Runtime.getRuntime().exec(command); // Start process
        IOUtils.copy(wkhtml.getErrorStream(), System.err); // Print output to console

        wkhtml.waitFor(); // Allow process to run
    }

    public static void main(String[] args){

        try {
            HtmltoPdf.makeAPdf();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
