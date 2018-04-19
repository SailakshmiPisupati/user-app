package experimentapp.kmai.org.userexperimentapp.setting_selector;

/**
 * Created by saila on 4/1/18.
 */

public class ModeSectionValues {

    public static int PhraseSection1_start = 0;
    public static int PhraseSection1_end = 9;
    public static int PhraseSection2_start = 10;
    public static int PhraseSection2_end = 19;
    public static int PhraseSection3_start = 20;
    public static int PhraseSection3_end = 29;
    public static int PhraseSection4_start = 30;
    public static int PhraseSection4_end = 39;

    public static int experimentSection1_start = 0;
    public static int experimentSection1_end = 9;
    public static int experimentSection2_start = 10;
    public static int experimentSection2_end = 19;
    public static int experimentSection3_start = 20;
    public static int experimentSection3_end = 29;
    public static int experimentSection4_start = 30;
    public static int experimentSection4_end = 39;


    public static int currentSession;
    public static int currentPhrase = 0;

    public static int getCurrentSession() {
        return currentSession;
    }

    public static void setCurrentSession(int currentSession) {
        ModeSectionValues.currentSession = currentSession;
    }

    public static int getCurrentPhrase() {
        return currentPhrase;
    }

    public static void setCurrentPhrase(int currentPhrase) {
        ModeSectionValues.currentPhrase = currentPhrase;
    }

    public static int getExperimentSection1_start() {
        return experimentSection1_start;
    }

    public static void setExperimentSection1_start(int experimentSection1_start) {
        ModeSectionValues.experimentSection1_start = experimentSection1_start;
    }

    public static int getExperimentSection1_end() {
        return experimentSection1_end;
    }

    public static void setExperimentSection1_end(int experimentSection1_end) {
        ModeSectionValues.experimentSection1_end = experimentSection1_end;
    }

    public static int getExperimentSection2_start() {
        return experimentSection2_start;
    }

    public static void setExperimentSection2_start(int experimentSection2_start) {
        ModeSectionValues.experimentSection2_start = experimentSection2_start;
    }

    public static int getExperimentSection2_end() {
        return experimentSection2_end;
    }

    public static void setExperimentSection2_end(int experimentSection2_end) {
        ModeSectionValues.experimentSection2_end = experimentSection2_end;
    }

    public static int getExperimentSection3_start() {
        return experimentSection3_start;
    }

    public static void setExperimentSection3_start(int experimentSection3_start) {
        ModeSectionValues.experimentSection3_start = experimentSection3_start;
    }

    public static int getExperimentSection3_end() {
        return experimentSection3_end;
    }

    public static void setExperimentSection3_end(int experimentSection3_end) {
        ModeSectionValues.experimentSection3_end = experimentSection3_end;
    }

    public static int getExperimentSection4_start() {
        return experimentSection4_start;
    }

    public static void setExperimentSection4_start(int experimentSection4_start) {
        ModeSectionValues.experimentSection4_start = experimentSection4_start;
    }

    public static int getExperimentSection4_end() {
        return experimentSection4_end;
    }

    public static void setExperimentSection4_end(int experimentSection4_end) {
        ModeSectionValues.experimentSection4_end = experimentSection4_end;
    }

    public static int section_first;
    public static int section_second;
    public static int section_third;
    public static int section_fourth;

    public static int getSection_first() {
        return section_first;
    }

    public static void setSection_first(int section_first) {
        ModeSectionValues.section_first = section_first;
    }

    public static int getSection_second() {
        return section_second;
    }

    public static void setSection_second(int section_second) {
        ModeSectionValues.section_second = section_second;
    }

    public static int getSection_third() {
        return section_third;
    }

    public static void setSection_third(int section_third) {
        ModeSectionValues.section_third = section_third;
    }

    public static int getSection_fourth() {
        return section_fourth;
    }

    public static void setSection_fourth(int section_fourth) {
        ModeSectionValues.section_fourth = section_fourth;
    }

}
