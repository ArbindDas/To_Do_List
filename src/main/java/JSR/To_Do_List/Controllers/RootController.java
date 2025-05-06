
package JSR.To_Do_List.Controllers;

import java.io.IOException;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import JSR.To_Do_List.Model.Customer;
import JSR.To_Do_List.Services.CustomerServices;
import JSR.To_Do_List.Services.EmailService;
import JSR.To_Do_List.Services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Controller
public class RootController {

    @Autowired
    private CustomerServices customerServices;

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/customers"; // Redirect to the customer page
    }

    @GetMapping("/customers")
    public String home(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        // If search term is provided, filter customers by name
        Page<Customer> customerPage;
        if (search != null && !search.isEmpty()) {
            customerPage = customerServices.searchCustomersByName(search, pageable);
        } else {
            customerPage = customerServices.getAllCustomersWithPagination(pageable);
        }

        model.addAttribute("customerPage", customerPage); // Pass Page object to the view
        model.addAttribute("currentPage", page); // Pass current page number
        model.addAttribute("totalPages", customerPage.getTotalPages()); // Pass total number of pages
        model.addAttribute("totalCustomers", customerPage.getTotalElements()); // Total customers count
        model.addAttribute("search", search); // Pass the search term to retain it in the input field



        return "home"; // Returns the Thymeleaf template
    }

    // Create Customer Page
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "create";
    }

    // Save Customer
    @PostMapping("/save")
    public String save(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        boolean isupdate = (customer.getId() !=null); 

        customerServices.saveCustomer(customer);

        if (isupdate) {
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        }else{
            redirectAttributes.addFlashAttribute("successMessage", "Customer added Successfully!");
        }

        // Add success message as a flash attribute
    
        // return "home"; // Your home page template
        return "redirect:/customers";
    }

    @GetMapping("/customer/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Customer customer = customerServices.findById(id).orElse(null);
        model.addAttribute("customer", customer);
        return "create";
    }

    @GetMapping("/customer/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {

        customerServices.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "customer deleted succesfully ");
        return "redirect:/customers";
    }

    @GetMapping("/customer/{id}")

    public String show(@PathVariable Long id, Model model) {
        customerServices.findById(id)
                .ifPresent(customer -> model.addAttribute("customer", customer));

        return "show";
    }

    @Autowired
    private PdfService pdfService;

    @GetMapping("/customer/{id}/download-pdf")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable Long id) throws IOException {
        Customer customer = customerServices.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        byte[] pdfContent = pdfService.generatePdf(customer);

        ByteArrayResource resource = new ByteArrayResource(pdfContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customer_" + customer.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfContent.length)
                .body(resource);
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/customer/{id}/share-via-email")
    public String shareViaEmail(@PathVariable Long id, @RequestParam String email)
            throws IOException, MessagingException, jakarta.mail.MessagingException {
        Customer customer = customerServices.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        byte[] pdfContent = pdfService.generatePdf(customer);

        emailService.sendEmailWithAttachment(
                email,
                "Customer Details",
                "Please find the customer details attached.",
                pdfContent,
                "customer_" + customer.getId() + ".pdf");

        return "redirect:/customer/" + id + "?success=Email sent successfully!";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }


    @PostMapping("/submit-contact")
    public String submitContact(@ModelAttribute ContactForm contactForm, Model model) {
        // Here you would typically process the form data (e.g., send email, save to database)
        model.addAttribute("success", "Thank you for your message! We'll get back to you soon.");
        return "contact";
    }



}
