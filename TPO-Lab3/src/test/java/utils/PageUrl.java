package utils;

public enum PageUrl {
    UCOZ("https://www.ucoz.ru/"),
    SIGNUP("https://www.ucoz.ru/register"),
    LOGIN("https://www.ucoz.ru/login"),
    CREATE_SITE("https://www.ucoz.ru/createsite"),
    TOUR("https://www.ucoz.ru/tour/"),
    BLOG("https://blog.ucoz.ru/"),
    FORUM("https://forum.ucoz.ru/"),
    PRICING("https://www.ucoz.ru/pricing/"),
    JOBS("https://ru.uteam.pro/"),
    EXAMPLES("https://www.ucoz.ru/success/"),
    HELP("https://www.ucoz.ru/help/"),
    CONTACT("https://www.ucoz.ru/contact/"),
    CREATESITE("https://www.ucoz.ru/createsite"),
    PANEL("https://tpo-lab3.ucoz.net/panel/");


    private final String url;

    PageUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
