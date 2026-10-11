package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.annotations.Route;
import com.university.direct_current_motor.adapters.desktop.common.components.Box;
import com.university.direct_current_motor.adapters.desktop.common.components.Screen;
import com.university.direct_current_motor.adapters.desktop.common.components.Typography;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

@Route(name = "theory-screen")
public final class TheoryScreen extends Screen {

    public static final class ContentSection extends Box {

        public ContentSection() {
            super();

            this.setDirection(Direction.Y_DIRECTION);
            this.setAlign(Align.START, Align.CENTER);
            this.addInlineStyles("-fx-background-color: #ffffff;");

            final ScrollPane scrollPane = new ScrollPane();

            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);

            scrollPane.setStyle("""
                    -fx-background-color: #ffffff;
                    -fx-background-insets: 0;
                    -fx-padding: 0;
                    """);

            final Box container = new Box(Direction.Y_DIRECTION);

            container.setAlign(Align.START, Align.CENTER);
            container.setPadding(40.0, 60.0);

            container.addInlineStyles("""
                    -fx-background-color: #ffffff;
                    """);

            container.getNode().setMinHeight(Region.USE_PREF_SIZE);

            final Typography mainTitle = new PrimaryTopography("ЛАБОРАТОРНА РОБОТА №4");
            mainTitle.addInlineStyles("""
                    -fx-font-size: 24px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #1a202c;
                    -fx-padding: 0 0 4 0;
                    """);

            final Typography subTitle = new PrimaryTopography(
                    "Дослідження двигуна постійного струму паралельного збудження"
            );
            subTitle.addInlineStyles("""
                    -fx-font-size: 18px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2d3748;
                    -fx-padding: 0 0 24 0;
                    """);

            // --- МЕТА РОБОТИ ---
            final Typography section1Title = new PrimaryTopography("МЕТА РОБОТИ");
            section1Title.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2b6cb0;
                    -fx-padding: 12 0 8 0;
                    """);

            final Typography section1Text = new PrimaryTopography(
                    """
                    1. Вивчити будову і принцип дії двигуна постійного струму паралельного збудження.
                    2. Вибрати контрольно-вимірювальні прилади для проведення дослідів.
                    3. Засвоїти способи пуску і регулювання частоти обертання якоря двигуна.
                    4. Дослідити двигун в режимі навантаження і побудувати механічну та робочі характеристики.
                    """
            );
            section1Text.addInlineStyles("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 6px;
                    -fx-padding: 0 0 20 0;
                    """);

            // --- ОСНОВНІ ТЕОРЕТИЧНІ ВІДОМОСТІ ---
            final Typography section2Title = new PrimaryTopography("ОСНОВНІ ТЕОРЕТИЧНІ ВІДОМОСТІ");
            section2Title.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2b6cb0;
                    -fx-padding: 12 0 8 0;
                    """);

            final Typography section2Text = new PrimaryTopography(
                    """
                    Будова електричних двигунів і генераторів постійного струму однакова. Їх принцип дії базується на взаємодії струму, що протікає в обмотці якоря, і магнітного поля, яке створюється головними полюсами машини. Двигуни постійного струму завжди живляться від мережі постійного струму.

                    При живленні двигуна від мережі в обмотках збудження і якоря протікають струми. Струм збудження створює магнітний потік Ф полюсів машини. Згідно із законом Ампера на провідники якоря, які знаходяться в магнітному полі, діють механічні сили, що створюють обертальний момент:
                    M = Cм * Ф * Iя
                    де См – конструктивний коефіцієнт моменту.

                    За законом електромагнітної індукції в обмотках якоря, що обертається, виникає проти-ЕРС (електрорушійна сила), яка направлена проти струму:
                    E = Ce * Ф * n
                    де СE – конструктивний коефіцієнт ЕРС, n – частота обертання якоря, Ф – магнітний потік.

                    Рівняння напруги на затискачах якоря двигуна:
                    U = E + Iя * Rя
                    де Rя – опір обмотки якоря, Iя – струм в обмотці якоря.

                    Струм в обмотці якоря та частота обертання визначаються за формулами:
                    Iя = (U - E) / Rя
                    n = (U - Iя * Rя) / (Ce * Ф)

                    Особливості пуску двигуна:
                    В момент ввімкнення двигуна (n = 0) ЕРС відсутня (Е = 0). Пусковий струм Iп = U / Rя обмежується тільки малим опором обмотки якоря і може перевищувати номінальний струм в 10-30 разів, що недопустимо. Щоб запобігти руйнуванню колектора й обмоток, на час пуску послідовно до кола якоря вмикають пусковий реостат Rп. Тоді пусковий струм дорівнює:
                    Iп = U / (Rя + Rп)

                    На початку пуску опір пускового реостата Rп максимальний. При збільшенні частоти обертання якоря двигуна опір пускового реостата плавно зменшують до нульового значення. Регулювання частоти обертання ДПС паралельного збудження здійснюють трьома способами: зміною підведеної напруги U, зміною опору кола якоря додатковим резистором або зміною магнітного потоку Ф (струму збудження Із) за допомогою регулювального реостата Rр. Обрив кола збудження під час роботи недопустимий, оскільки це призводить до аварійного зростання швидкості («розносу» двигуна).
                    """
            );
            section2Text.addInlineStyles("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 6px;
                    -fx-max-width: 900px;
                    -fx-padding: 0 0 20 0;
                    """);

            // --- ОПИС ЛАБОРАТОРНОЇ УСТАНОВКИ ---
            final Typography section3Title = new PrimaryTopography("ОПИС ЛАБОРАТОРНОЇ УСТАНОВКИ");
            section3Title.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2b6cb0;
                    -fx-padding: 12 0 8 0;
                    """);

            final Typography section3Text = new PrimaryTopography(
                    """
                    Об’єктом дослідження служить двигун постійного струму паралельного збудження, який живиться від мережі постійного струму через автоматичний двополюсний вимикач SF1. В колах двигуна ввімкнений пусковий Rп і регулювальний Rр реостати, а також вимірювальні прилади: амперметр якоря PA1, амперметр збудження PA2, та вольтметр мережі якоря PV1. Частота обертання вала двигуна вимірюється тахометром.

                    Навантаженням двигуна постійного струму служать обмотки трифазного асинхронного двигуна (АД) з короткозамкнутим ротором та реостат навантаження Rнав, які з’єднані послідовно. Потужність навантаження визначається за показами вимірювальних приладів PA3 (струм навантаження) i PV2 (напруга навантаження) в колі навантаження.
                    """
            );
            section3Text.addInlineStyles("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 6px;
                    -fx-max-width: 900px;
                    -fx-padding: 0 0 20 0;
                    """);

            // --- ПОРЯДОК ВИКОНАННЯ РОБОТИ ---
            final Typography section4Title = new PrimaryTopography("ПОРЯДОК ВИКОНАННЯ РОБОТИ / ХІД РОБОТИ");
            section4Title.addInlineStyles("""
                    -fx-font-size: 16px;
                    -fx-font-weight: bold;
                    -fx-text-fill: #2b6cb0;
                    -fx-padding: 12 0 8 0;
                    """);

            final Typography section4Text = new PrimaryTopography(
                    """
                    Завдання 1. Пуск двигуна постійного струму
                    1. Ознайомитися з електричною схемою дослідження на головному екрані.
                    2. Перед пуском переконатися, що пусковий реостат Rп введено повністю (опір максимальний), а регулювальний реостат Rр в колі збудження повністю виведено (опір мінімальний, що забезпечує максимальний магнітний потік).
                    3. Замкнути автоматичний вимикач SF1. При поступовому зростанні частоти обертання вала плавно вивести пусковий реостат Rп до нульового (мінімального) значення опору.

                    Завдання 2. Дослідження впливу напруги якоря на частоту обертання
                    1. За допомогою пускового реостата Rп встановлювати різні значення напруги на затискачах якоря (контролювати за вольтметром PV1).
                    2. Фіксувати відповідну частоту обертання валу двигуна n за допомогою цифрового тахометра.
                    3. Впродовж всього досліду за допомогою регулювального реостата Rр підтримувати сталий номінальний струм збудження: Із = const. Результати записати в звіт.

                    Завдання 3. Дослідження впливу струму збудження на частоту обертання
                    1. За допомогою регулювального реостата Rр встановлювати різні значення струму збудження (контролювати за амперметром PA2).
                    2. Фіксувати відповідні частоти обертання валу двигуна n.
                    3. Впродовж усього досліду за допомогою пускового реостата Rп підтримувати на затискачах якоря двигуна сталу номінальну напругу: Uя = const. Результати записати в звіт.

                    Завдання 4. Дослідження двигуна в режимі навантаження
                    1. Встановити реостат навантаження Rнав в положення максимального опору. Замкнути однополюсний вимикач SA1.
                    2. Реостатами Rп та Rр встановити номінальну напругу двигуна Uн та номінальний струм збудження Ізн. Записати початкові покази приладів.
                    3. Покроково змінювати навантаження двигуна за допомогою регулювання реостата Rнав (виконати 6-7 дослідів).
                    4. Для кожного досліду за допомогою реостатів Rп і Rр підтримувати сталими номінальну напругу Uн та номінальний струм збудження Ізн. Заносити дані до таблиці досліджень кнопкою 'Зберегти дослідні дані'.
                    """
            );
            section4Text.addInlineStyles("""
                    -fx-font-size: 14px;
                    -fx-text-fill: #2d3748;
                    -fx-line-spacing: 6px;
                    -fx-max-width: 900px;
                    -fx-padding: 0 0 20 0;
                    """);

            container.addChildren(
                    mainTitle,
                    subTitle,
                    section1Title,
                    section1Text,
                    section2Title,
                    section2Text,
                    section3Title,
                    section3Text,
                    section4Title,
                    section4Text
            );

            scrollPane.setContent(container.getNode());

            this.getNode().getChildren().add(scrollPane);
        }
    }

    private final Header header;
    private final ContentSection contentSection;

    public TheoryScreen() {
        super();

        this.header = new Header();
        this.contentSection = new ContentSection();

        this.applyLayout();

        this.addChildren(
                this.header,
                this.contentSection
        );
    }

    private void applyLayout() {
        this.header.bindWidthPercent(this, 100.0);
        this.header.setHeight(80.0);

        this.contentSection.bindWidthPercent(this, 100.0);
        this.contentSection.bindHeightPercent(this, 100.0, 80);
    }
}