package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.annotations.Route;
import com.university.direct_current_motor.adapters.desktop.common.components.Box;
import com.university.direct_current_motor.adapters.desktop.common.components.Screen;
import com.university.direct_current_motor.adapters.desktop.common.components.Typography;

@Route(name = "about-us-screen")
public final class AboutUsScreen extends Screen {

    public static final class ContentSection extends Box {

        public ContentSection() {
            super();
            this.setDirection(Direction.Y_DIRECTION);
            this.setAlign(Align.START, Align.CENTER);
            this.setPadding(60.0, 80.0);

            final Typography title = new PrimaryTopography("Про програму");
            title.addInlineStyles("""
                    -fx-font-size: 30px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #1a202c;
                    -fx-padding: 0 0 24 0;
                    -fx-text-alignment: center;
            """);

            final Typography projectDescription = new PrimaryTopography(
                    "Програмний комплекс розроблено для цифровізації лабораторних занять та " +
                            "віртуального моделювання процесів роботи двигуна постійного струму."
            );
            projectDescription.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-text-fill: #718096;
                    -fx-max-width: 850px;
                    -fx-padding: 0 0 48 0;
                    -fx-text-alignment: center;
            """);

            final Box infoBox = new Box(Direction.Y_DIRECTION);
            infoBox.setAlign(Align.CENTER, Align.CENTER);

            final Typography authorsTitle = new PrimaryTopography("Автори проєкту:");
            authorsTitle.addInlineStyles("""
                    -fx-font-size: 20px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2d3748;
                    -fx-padding: 0 0 8 0;
                    -fx-text-alignment: center;
            """);

            final Typography developerInfo = new PrimaryTopography(
                    "Розробник:\nСтудент групи АКП-25-2 Сіліфанов А. В."
            );
            developerInfo.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 8px;
                    -fx-text-alignment: center;
            """);

            final Typography consultantInfo = new PrimaryTopography(
                    "Консультант:\nАсистент кафедри ЕІ Боднарчук А. П."
            );
            consultantInfo.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 8px;
                    -fx-text-alignment: center;
            """);

            final Typography footerInfo = new PrimaryTopography(
                    "Кафедра електротехніки та інженерної екології\n" +
                            "Івано-Франківський національний технічний університет нафти і газу (ІФНТУНГ), 2026"
            );
            footerInfo.addInlineStyles("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #a0aec0;
                    -fx-line-spacing: 6px;
                    -fx-padding: 60 0 0 0;
                    -fx-text-alignment: center;
            """);

            infoBox.addChildren(authorsTitle, developerInfo, consultantInfo);
            this.addChildren(title, projectDescription, infoBox, footerInfo);
        }
    }

    private final Header header;
    private final ContentSection contentSection;

    public AboutUsScreen() {
        super();
        this.header = new Header();
        this.contentSection = new ContentSection();

        this.applyLayout();
        this.addChildren(this.header, this.contentSection);
    }

    private void applyLayout() {
        this.header.bindWidthPercent(this, 100.0);
        this.header.setHeight(80.0);

        this.contentSection.bindWidthPercent(this, 100.0);
        this.contentSection.bindHeightPercent(this, 100.0, 80);
    }
}