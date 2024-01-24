package myprj.learning.myjpa.web;


import myprj.learning.myjpa.domain.Album;
import myprj.learning.myjpa.domain.Book;
import myprj.learning.myjpa.domain.Movie;
import myprj.learning.myjpa.domain.Product;
import myprj.learning.myjpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String productList(Model model) {
        model.addAttribute("productList", this.productService.findAll());
        return "list-product";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String registProductGet() {
        return "regist-product";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String registProductPost(Model model,
                                    String title,
                                    String price,
                                    String stock,
                                    String type,
                                    String author,
                                    String artist,
                                    String director) {

        ////////////////////////////////////////////////
        // 아무리 봐도 이건 아닌 거 같은데.... 이거 어떻게 리팩토링 해야 하지
        switch (type) {
            case "album" :
                Album album = new Album();
                album.setArtist(artist);
                album.setTitle(title);
                album.setPrice(Integer.valueOf(price));
                album.setStock(Integer.valueOf(stock));
                Long albumSeq = this.productService.saveProduct(album);
                model.addAttribute("product", this.productService.findById(albumSeq));
                break;
            case "book" :
                Book book = new Book();
                book.setAuthor(author);
                book.setTitle(title);
                book.setPrice(Integer.valueOf(price));
                book.setStock(Integer.valueOf(stock));
                this.productService.saveProduct(book);
                Long bookSeq = this.productService.saveProduct(book);
                model.addAttribute("product", this.productService.findById(bookSeq));
                break;
            case "movie" :
                Movie movie = new Movie();
                movie.setDirector(director);
                movie.setTitle(title);
                movie.setPrice(Integer.valueOf(price));
                movie.setStock(Integer.valueOf(stock));
                this.productService.saveProduct(movie);
                Long movieSeq = this.productService.saveProduct(movie);
                model.addAttribute("product", this.productService.findById(movieSeq));
                break;
        }
        return "regist-complete";
    }
}
