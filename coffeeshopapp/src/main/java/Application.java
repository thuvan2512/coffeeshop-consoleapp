import services.DepartmentService;
import utils.Presentation;

import java.io.IOException;
import java.text.ParseException;

public class Application {
    public static void main(String[] args) throws ParseException, IOException {
        DepartmentService.initListDepartment();
        Presentation.showMenu();

    }
}
