package pl.home.ui.commons;


import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import pl.home.navigators.UniversityNavigator;
import pl.home.ui.students.StudentLayoutFactory;


@SpringUI(path = UniversityMainUI.NAME)
public class UniversityMainUI extends UI {

    public static final String NAME = "/ui";

    private final UIComponentBuilder logoComponentBuilder;
    private final UIComponentBuilder menuComponentBuilder;
    private final ApplicationContext applicationContext;
    private final SpringViewProvider viewProvider;

    private Panel changeTab = new Panel();

    public UniversityMainUI(@Qualifier("universityLogoLayoutFactory") UIComponentBuilder logoComponentBuilder,
                            @Qualifier("universityMenuLayoutFactory") UIComponentBuilder menuComponentBuilder, ApplicationContext applicationContext, SpringViewProvider viewProvider) {
        this.logoComponentBuilder = logoComponentBuilder;
        this.menuComponentBuilder = menuComponentBuilder;
        this.applicationContext = applicationContext;
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        changeTab.setHeight("100%");

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(true);

        Panel contentPanel = new Panel();
        contentPanel.setWidth("75%");
        contentPanel.setHeight("100%");

        Panel logoPanel = new Panel();
        logoPanel.setWidth("75%");
        logoPanel.setHeightUndefined();

        HorizontalLayout uiLayout = new HorizontalLayout();
        uiLayout.setSizeFull();
        uiLayout.setMargin(true);

        Component logo = logoComponentBuilder.createComponent();
        Component menu = menuComponentBuilder.createComponent();


        uiLayout.addComponent(menu);
        uiLayout.addComponent(changeTab);

        uiLayout.setComponentAlignment(changeTab, Alignment.TOP_CENTER);
        uiLayout.setComponentAlignment(menu, Alignment.TOP_CENTER);

        uiLayout.setExpandRatio(menu, 1);
        uiLayout.setExpandRatio(changeTab, 2);

        logoPanel.setContent(logo);
        contentPanel.setContent(uiLayout);

        rootLayout.addComponent(logoPanel);
        rootLayout.addComponent(contentPanel);
        rootLayout.setComponentAlignment(contentPanel, Alignment.MIDDLE_CENTER);
        rootLayout.setComponentAlignment(logoPanel, Alignment.TOP_CENTER);
        rootLayout.setExpandRatio(contentPanel, 1);

        initNavigator();



        setContent(rootLayout);
    }

    private void initNavigator() {
        UniversityNavigator navigator = new UniversityNavigator(this,changeTab);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(navigator);
        navigator.addProvider(viewProvider);
        navigator.navigateTo(StudentLayoutFactory.NAME);

    }
}