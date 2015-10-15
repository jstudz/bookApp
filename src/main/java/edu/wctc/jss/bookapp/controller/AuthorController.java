package edu.wctc.jss.bookapp.controller;

import edu.wctc.jss.bookapp.model.Author;
import edu.wctc.jss.bookapp.model.AuthorDaoStrategy;
import edu.wctc.jss.bookapp.model.AuthorService;
import edu.wctc.jss.bookapp.model.DBStrategy;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_UPDATE_PAGE = "/addUpdateAuthor.jsp";
    private static final String INDEX_PAGE = "index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_EDIT_ACTION = "addEditDelete";
    private static final String SAVE_ACTION = "save";
    private static final String ACTION_PARAM = "action";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoStrategyClassName;
    private DBStrategy db;
    private AuthorDaoStrategy authDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        Class dbClass = Class.forName(dbStrategyClassName);
        db = (DBStrategy) dbClass.newInstance();
        Class daoClass = Class.forName(daoStrategyClassName);
        Constructor constructor = null;
        try {
            constructor = daoClass.getConstructor(new Class[]{DBStrategy.class, String.class, String.class, String.class, String.class});
        } catch (Exception e) {

        }

        if (constructor != null) {
            authDao = (AuthorDaoStrategy) constructor.newInstance(db, driverClass, url, userName, password);
        } else {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/book");
            constructor = daoClass.getConstructor(new Class[]{DataSource.class, DBStrategy.class});
            authDao = (AuthorDaoStrategy) constructor.newInstance(ds, db);
        }

        AuthorService authServ = new AuthorService(authDao);

        try {
            switch (action) {
                case LIST_ACTION: {

                    List<Author> authors = authServ.getAllAuthors();
                    request.setAttribute("authors", authors);
                    destination = LIST_PAGE;

                    break;
                }
                case ADD_EDIT_ACTION:
                    String addEdit = request.getParameter("addEdit");
                    if (addEdit != null) {
                        if (request.getParameter("authorId") == null) {
                            Author author = new Author();
                            request.setAttribute("author", author);
                            destination = ADD_UPDATE_PAGE;

                        } else {
                            String authId = request.getParameter("authorId");
                            Author author = authServ.getAuthorById(authId);
                            request.setAttribute("author", author);
                            destination = ADD_UPDATE_PAGE;

                        }
                    } else {
                        String authId = request.getParameter("authorId");
                        Author author = authServ.getAuthorById(authId);
                        authServ.deleteAuthor(author);

                        List<Author> authors = authServ.getAllAuthors();
                        request.setAttribute("authors", authors);
                        destination = LIST_PAGE;

                    }
                    break;
                case SAVE_ACTION:
                    String authId = request.getParameter("authorId");
                    if (authId.equals("0") || authId == null) {
                        Author author = new Author();
                        author.setAuthorName(request.getParameter("authorName"));
                        author.setDateCreated(sdf.parse(request.getParameter("dateCreated")));
                        authServ.addNewAuthor(author);
                    } else {
                        Author author = authServ.getAuthorById(authId);
                        author.setAuthorName(request.getParameter("authorName"));
                        author.setDateCreated(sdf.parse(request.getParameter("dateCreated")));
                        authServ.updateAuthor(author);
                    }
                    List<Author> authors = authServ.getAllAuthors();
                    request.setAttribute("authors", authors);
                    destination = LIST_PAGE;

                    break;
                default:
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;

                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);

    }

    @Override
    public void init() throws ServletException{
        driverClass = getServletConfig().getInitParameter("driverClass");
        url = getServletConfig().getInitParameter("url");
        userName = getServletConfig().getInitParameter("userName");
        password = getServletConfig().getInitParameter("password");
        dbStrategyClassName = this.getServletConfig().getInitParameter("dbStrategy");
        daoStrategyClassName = this.getServletConfig().getInitParameter("authorDao");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
