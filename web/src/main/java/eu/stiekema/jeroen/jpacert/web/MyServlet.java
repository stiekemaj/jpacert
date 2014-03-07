package eu.stiekema.jeroen.jpacert.web;

import eu.stiekema.jeroen.jpacert.Address;
import eu.stiekema.jeroen.jpacert.Employee;
import eu.stiekema.jeroen.jpacert.EmployeeService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jeroen Stiekema
 */
public class MyServlet extends HttpServlet {
    @EJB
    private EmployeeService bean;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("createEmployee".equals(action)) {
            String name = request.getParameter("name");
            String salary = request.getParameter("salary");
            long id = bean.createEmployee(name, Long.parseLong(salary));
            Employee employee = bean.findEmployeeById(id);
            response.getOutputStream().println("Employee created");
            response.getOutputStream().println(employee.toString());
        }

        if ("createAddress".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            bean.createAddress(id, street, city, state);
            Address address = bean.findAddressById(id);
            response.getOutputStream().println("Address created");
            response.getOutputStream().println(address.toString());
        }

        if ("findAddressById".equals(action)) {
            long id = Long.parseLong(request.getParameter("id"));
            response.getOutputStream().println(bean.findAddressById(id).toString());
        }

        if ("findEmployeeById".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            response.getOutputStream().println(bean.findEmployeeById(id).toString());
        }

        if ("addAddressToEmployee".equals(action)) {
            Long employeeId = Long.parseLong(request.getParameter("employeeId"));
            Long addressId = Long.parseLong(request.getParameter("addressId"));
            Employee employee = bean.findEmployeeById(employeeId);
            Address address = bean.findAddressById(addressId);
            bean.addAddressToEmployee(employee, address);
            Employee updatedEmployee = bean.findEmployeeById(employeeId);
            response.getOutputStream().println(updatedEmployee.toString());
        }
    }
}
