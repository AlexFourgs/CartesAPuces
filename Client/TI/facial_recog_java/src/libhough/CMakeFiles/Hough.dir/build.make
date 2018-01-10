# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.10

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough

# Include any dependencies generated for this target.
include CMakeFiles/Hough.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Hough.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Hough.dir/flags.make

facial_recog_houghJAVA_wrap.c: /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.i
facial_recog_houghJAVA_wrap.c: /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.i
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold --progress-dir=/home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Swig source"
	/usr/bin/cmake -E make_directory /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough
	/usr/bin/swig -java -outdir /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough -I/usr/lib/jvm/default/include -I/usr/lib/jvm/default/include/linux -o /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/facial_recog_houghJAVA_wrap.c /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.i

example.java: facial_recog_houghJAVA_wrap.c
	@$(CMAKE_COMMAND) -E touch_nocreate example.java

exampleJNI.java: facial_recog_houghJAVA_wrap.c
	@$(CMAKE_COMMAND) -E touch_nocreate exampleJNI.java

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o: CMakeFiles/Hough.dir/flags.make
CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o: facial_recog_houghJAVA_wrap.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o   -c /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/facial_recog_houghJAVA_wrap.c

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/facial_recog_houghJAVA_wrap.c > CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.i

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/facial_recog_houghJAVA_wrap.c -o CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.s

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.requires:

.PHONY : CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.requires

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.provides: CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.requires
	$(MAKE) -f CMakeFiles/Hough.dir/build.make CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.provides.build
.PHONY : CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.provides

CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.provides.build: CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o


CMakeFiles/Hough.dir/facial_recog_hough.cpp.o: CMakeFiles/Hough.dir/flags.make
CMakeFiles/Hough.dir/facial_recog_hough.cpp.o: /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/Hough.dir/facial_recog_hough.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Hough.dir/facial_recog_hough.cpp.o -c /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.cpp

CMakeFiles/Hough.dir/facial_recog_hough.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Hough.dir/facial_recog_hough.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.cpp > CMakeFiles/Hough.dir/facial_recog_hough.cpp.i

CMakeFiles/Hough.dir/facial_recog_hough.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Hough.dir/facial_recog_hough.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/facial_recog_hough.cpp -o CMakeFiles/Hough.dir/facial_recog_hough.cpp.s

CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.requires:

.PHONY : CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.requires

CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.provides: CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.requires
	$(MAKE) -f CMakeFiles/Hough.dir/build.make CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.provides.build
.PHONY : CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.provides

CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.provides.build: CMakeFiles/Hough.dir/facial_recog_hough.cpp.o


# Object files for target Hough
Hough_OBJECTS = \
"CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o" \
"CMakeFiles/Hough.dir/facial_recog_hough.cpp.o"

# External object files for target Hough
Hough_EXTERNAL_OBJECTS =

libHough.so: CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o
libHough.so: CMakeFiles/Hough.dir/facial_recog_hough.cpp.o
libHough.so: CMakeFiles/Hough.dir/build.make
libHough.so: CMakeFiles/Hough.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Linking CXX shared module libHough.so"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Hough.dir/link.txt --verbose=$(VERBOSE)
	/usr/bin/cmake -E copy_if_different /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/libHough.so /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough

# Rule to build all files generated by this target.
CMakeFiles/Hough.dir/build: libHough.so

.PHONY : CMakeFiles/Hough.dir/build

CMakeFiles/Hough.dir/requires: CMakeFiles/Hough.dir/facial_recog_houghJAVA_wrap.c.o.requires
CMakeFiles/Hough.dir/requires: CMakeFiles/Hough.dir/facial_recog_hough.cpp.o.requires

.PHONY : CMakeFiles/Hough.dir/requires

CMakeFiles/Hough.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Hough.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Hough.dir/clean

CMakeFiles/Hough.dir/depend: facial_recog_houghJAVA_wrap.c
CMakeFiles/Hough.dir/depend: example.java
CMakeFiles/Hough.dir/depend: exampleJNI.java
	cd /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c /home/antoine/git/CartesAPuces/Client/TI/facial_reco_c /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough /home/antoine/git/CartesAPuces/Client/TI/facial_recog_java/src/libhough/CMakeFiles/Hough.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Hough.dir/depend

