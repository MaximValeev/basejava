package model;

public enum ContactType {
    PHONE("Тел.") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": <a href=tel:" + value + ">" + value + "</a>";
        }
    },
    EXTRA_PHONE("Доп. тел.") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": <a href='tel:" + value + "'>" + value + "</a>";
        }
    },
//    HOME_PHONE("Домашний тел.") {
//        @Override
//        public String toHtml0(String value) {
//            return getTitle() + ": <a href='tel:" + value + "'>" + value + "</a>";
//        }
//    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "?call'>Skype</a>";
        }
    },
    MAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return "<a href='//" + value + "'>Профиль LinkedIn</a>";
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return "<a href='//" + value + "'>Профиль GitHub</a>";
        }
    },
    STATCKOVERFLOW("Профиль Stackoverflow"){
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>Профиль StackOverflow</a>";
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return "<a href='//" + value + "'>" + getTitle() + "</a>";
        }
    };


    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml0(String value) {
        return title + ": " + value;

    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);

    }
}
