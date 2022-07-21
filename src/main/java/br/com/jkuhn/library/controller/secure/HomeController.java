package br.com.jkuhn.library.controller.secure;

import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.entity.Car;
import br.com.jkuhn.library.services.implementations.BookServiceImpl;
import br.com.jkuhn.library.services.implementations.CarServiceImpl;
import br.com.jkuhn.library.services.interfaces.CarService;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;
import org.zkoss.zul.ext.Selectable;

import java.util.List;
import java.util.Set;

public class HomeController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @WireVariable
    private BookServiceImpl bookServiceImpl;

    @Wire
    private Textbox keywordBox;
    @Wire
    private Listbox carListbox;
    @Wire
    private Label modelLabel;
    @Wire
    private Label makeLabel;
    @Wire
    private Label priceLabel;
    @Wire
    private Label descriptionLabel;
    @Wire
    private Image previewImage;
    @Wire
    private Component detailBox;

    @Wire
    private Textbox name;

    private CarService carService = new CarServiceImpl();

    @Listen("onClick = #searchButton")
    public void search(){
        String keyword = keywordBox.getValue();
        List<Car> result = carService.search(keyword);
        carListbox.setModel(new ListModelList<Car>(result));

        List<Book> livros = bookServiceImpl.findAll();
        System.out.println(livros);
    }

    @Listen("onSelect = #carListbox")
    public void showDetail(){
        detailBox.setVisible(true);

        Set<Car> selection = ((Selectable<Car>)carListbox.getModel()).getSelection();
        if (selection!=null && !selection.isEmpty()){
            Car selected = selection.iterator().next();
            previewImage.setSrc(selected.getPreview());
            modelLabel.setValue(selected.getModel());
            makeLabel.setValue(selected.getMake());
            priceLabel.setValue(selected.getPrice().toString());
            descriptionLabel.setValue(selected.getDescription());
        }
    }
}
