/**
 * 
 */
/**
 * 
 */
module MyDetectiveGame {
    requires com.fasterxml.jackson.databind;
    exports Core;
    opens Core;
    exports JsonDTO;
    opens JsonDTO; // Add this line for reflection
}