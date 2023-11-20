package com.example.proiect;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.lowagie.text.DocumentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user/")
public class userController {

    @Autowired
    private repo userRepo;

    @Autowired
    private requestsRepo requestsRepo;

    @Autowired
    private serviceImpl service;

    @ModelAttribute
    private void userDetails(Model m , Principal p){
       String email =  p.getName();
       UserDtls user = userRepo.findByEmail(email);
       m.addAttribute("user" , user);
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/makeRequest")
    public String makeRequest(){
        return "makeRequest";
    }

    @GetMapping("/requests")
    public String seeRequests(){
        return "requests";
    }

    

    @ModelAttribute
    private void seeRequests(Model m , Principal p){
        String email =  p.getName();
        UserDtls user = userRepo.findByEmail(email);
        int userId = user.getId();
        System.out.println("Am ajuns aici cu userul: " + userId);
        List<Request> requests = new ArrayList<>(service.getRequestsByUserId(userId));
        System.out.println("Am ajuns aici cu userul: " + userId);
        for(int i = 0; i < requests.size(); i ++){
            System.out.println("calea requestului: " + requests.get(i).getPath());
        }
        m.addAttribute("requesturi" , requests);
    }

    @GetMapping("/downloadPDF/{filename:.+}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadPDF(@PathVariable String filename) throws IOException {
        // Calea către folderul din interiorul containerului unde se află fișierul PDF
        String folderPathInContainer = "/home/pdfs/";
        System.out.println("a gasit folderul");

        String separator = "_home_pdfs_";
        int separatorIndex = filename.indexOf(separator);
            // Extragem doar partea corespunzătoare numelui fișierului
        String fileNameFinal = filename.substring(separatorIndex + separator.length());

        System.out.println("Numele fișierului: " + fileNameFinal);

        // Căutați fișierul PDF în folderul din interiorul containerului
        File file = new File(folderPathInContainer + fileNameFinal);



        // Verificați dacă fișierul există
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("a gasit fisierul" + filename);


        System.out.println("verificam daca exista");

        // Încărcați fișierul într-un ByteArrayResource pentru a-l putea trimite ca răspuns
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        System.out.println("trimitem ca raspuns 1");

        HttpHeaders headers = new HttpHeaders();
        System.out.println("trimitem ca raspuns 2");

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

        System.out.println("trimitem ca raspuns 3");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }



    @ModelAttribute("altiUseri")
    public List<String> getUsers(Principal p){
        String email =  p.getName();
        UserDtls user = userRepo.findByEmail(email);
        List<UserDtls>otherUsers =new ArrayList<>(service.cautaObiecteCuEmailDiferitSiRol(user));
        List<String>elemente = new ArrayList<>();
        for(int i = 0 ; i < otherUsers.size(); i ++){
            elemente.add(otherUsers.get(i).getFirstName() + " " + otherUsers.get(i).getLastName());
        }
        System.out.println("useri gasiti: " + elemente);
        return elemente;
    }

    @ModelAttribute("companies")
    public List<Companies> getCompanies(Principal p){
        String email =  p.getName();
        UserDtls user = userRepo.findByEmail(email);
        List<Companies>userCompanies =new ArrayList<>(service.searchCompaniesByUser(user));
        return userCompanies;
    }




    @PostMapping("/makeVacationRequest")
    public String makeVacationRequest(@ModelAttribute userEntity userE , Principal p , @RequestParam("from_date")String fDate ,@RequestParam("to_date")String tDate,@RequestParam("replacement")String replacement) throws DocumentException, IOException {

        System.out.println("am intrat:");
        String email =  p.getName();
        System.out.println("am intrat:" + email);
        UserDtls user = userRepo.findByEmail(email);
        System.out.println("user: " + user.getEmail());

        userE.setPosition(user.getPosition());
        userE.setReplacement(replacement);
        userE.setName(user.getFirstName() + " " + user.getLastName());
        userE.setPosition(user.getPosition());

        LocalDate startDate = LocalDate.parse(fDate);
        LocalDate endDate = LocalDate.parse(tDate);

        LocalDate startDate1 = startDate;

//        int year = startDate.getYear();
//        int month = startDate.getMonthValue();
//        int day = startDate.getDayOfMonth();

        int count = 0;

        while (!startDate1.isAfter(endDate)) {
            DayOfWeek dayOfWeek = startDate1.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                count++;
            }
            startDate1 = startDate1.plus(1, ChronoUnit.DAYS);
        }


        int numarZile = (int) ChronoUnit.DAYS.between(startDate, endDate)+1;
        numarZile = numarZile - count;
        userE.setNumber_of_days(numarZile);
        ArrayList<String> selectedCompanies = new ArrayList<>();
        for(Companies i : user.getCompanies()){
            selectedCompanies.add(i.getName());
        }
        userE.setCompany(selectedCompanies);
        System.out.println(userE);
        userController thymeleaf2Pdf = new userController();

        List<String>paths = thymeleaf2Pdf.generatePdfFromHtml(userE);

        List<Request>requests = new ArrayList<>();
        for(int i = 0; i < paths.size(); i ++){
            Request cerere = new Request();
            cerere.setFromDate(fDate);
            System.out.println("from date: " + cerere.getFromDate());
            cerere.setToDate(tDate);
            System.out.println("to date: " + cerere.getToDate());
            cerere.setUser(user);
            System.out.println("user: " + cerere.getUser().getEmail());
            cerere.setPath(paths.get(i));
            System.out.println("locatie: " + cerere.getPath());
            String[] arrOfStr = replacement.split(" ");
            UserDtls u = userRepo.findByFirstNameAndLastName(arrOfStr[0] , arrOfStr[1]);
            cerere.setCompany(selectedCompanies.get(i));
            System.out.println("companie: " + cerere.getCompany());
            System.out.println("inlocuitor: " + u.getEmail());
            cerere.setReplacement(u);
            System.out.println("inlocuitor email: " +cerere.getReplacement().getEmail());
            requests.add(cerere);
            System.out.println("am trecut pe aici");
        }
//        for(int i = 0; i < paths.size(); i ++){
//            requests.get(i).setPath(paths.get(i));
//        }


//        for(int i = 0; i < requests.size(); i ++){
//            requests.get(i).setFromDate(fDate);
//            requests.get(i).setToDate(tDate);
//        }
//        for(int i = 0; i < requests.size(); i ++){
//            user.addRequest(requests.get(i));
//        }
//        System.out.println("cereri: " + requests);
        for(int i = 0; i < requests.size(); i ++){
            requestsRepo.save(requests.get(i));
            System.out.println("am adaugat cerere");
        }
        return "redirect:/user/makeRequest";
    }

    public String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
    }



    public List<String> generatePdfFromHtml( userEntity me) throws IOException, DocumentException {

        List<String> pdfFilePaths = new ArrayList<>();


        for (String s : me.getCompany()) {
            String html = this.parseThymeleafTemplate(me, s);

//            String outputFolder = System.getProperty("user.home") + File.separator + me.getName().replaceAll("\\s","")+"_"+ s.replaceAll("\\s","_")+"_"+me.getDate_of_completion()+".pdf";
//            String outputFolder = "/home/pdfs/" + me.getName().replaceAll("\\s","")+"_"+ s.replaceAll("\\s","_")+"_"+me.getDate_of_completion()+".pdf";
//            String outputFolder = "/home/pdfs/" + me.getName().replaceAll("\\s", "") + "_" + s.replaceAll("\\s", "_") + "_" + me.getDate_of_completion() + ".pdf";
//            String outputFolder = "/home/pdfs/" + me.getName().replaceAll("\\s", "") + "_" + s.replaceAll("\\s", "_") + "_" + me.getDate_of_completion() + ".pdf";
            String outputFileName = me.getName() + "_" + s + "_" + me.getDate_of_completion() + ".pdf";
            String sanitizedFileName = sanitizeFileName(outputFileName);
            String outputFolder = "/home/pdfs/" + sanitizedFileName;



            pdfFilePaths.add(outputFolder);
            OutputStream outputStream = new FileOutputStream(outputFolder);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();

        }
        return pdfFilePaths;
    }

    private String parseThymeleafTemplate(userEntity me, String company) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        int nr_de_zile = 1;
        String mesaj_1_day = "unei zile de concediu de odihna";
        String mesaj_n_days= " a "+me.getNumber_of_days()+"  zile de concediu de odihna ";
        String period = me.getNumber_of_days() == 1 ? "in data de " + me.getFrom_date() : "din data de " +me.getFrom_date()+" pana in " + me.getTo_date();
        context.setVariable("employee", me.getName());
        context.setVariable("company", company);
        context.setVariable("position", me.getPosition());
        context.setVariable("days", me.getNumber_of_days() == 1 ? mesaj_1_day : mesaj_n_days);
        context.setVariable("period",period);
        context.setVariable("year",me.getYear());
        context.setVariable("replacement",me.getReplacement());
        context.setVariable("dateOfCompletion",me.getDate_of_completion());

        return templateEngine.process("grx_template", context);
    }


}
