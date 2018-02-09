javadoc ^
-cp ./lib/* ^
-private ^
-doclet com.sun.tools.doclets.doccheck.DocCheck ^
-docletpath ./lib/doccheck.jar ^
-encoding UTF-8 ^
-d ./doccheck ^
-sourcepath ./src ^
-subpackages juego