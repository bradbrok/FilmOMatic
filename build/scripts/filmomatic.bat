@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  filmomatic startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and FILMOMATIC_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\filmomatic-0.1.jar;%APP_HOME%\lib\kotlin-stdlib-jre8-1.1.60.jar;%APP_HOME%\lib\kotlinx-coroutines-core-0.19.3.jar;%APP_HOME%\lib\pi4j-core-1.1.jar;%APP_HOME%\lib\tornadofx-1.7.12.jar;%APP_HOME%\lib\moshi-kotlin-1.5.0.jar;%APP_HOME%\lib\kotlin-stdlib-1.1.60.jar;%APP_HOME%\lib\kotlin-stdlib-jre7-1.1.60.jar;%APP_HOME%\lib\javax.json-1.0.4.jar;%APP_HOME%\lib\kotlin-reflect-1.1.51.jar;%APP_HOME%\lib\moshi-1.5.0.jar;%APP_HOME%\lib\okio-1.13.0.jar;%APP_HOME%\lib\annotations-13.0.jar

@rem Execute filmomatic
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %FILMOMATIC_OPTS%  -classpath "%CLASSPATH%" MainKt %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable FILMOMATIC_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%FILMOMATIC_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
