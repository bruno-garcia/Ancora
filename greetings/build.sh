cargo build --target i686-linux-android --release
cargo build --target aarch64-linux-android --release
# cargo build --target armv7-linux-androideabi --release

cd ../app/src/main

mkdir -p jniLibs
mkdir -p jniLibs/arm64
mkdir -p jniLibs/x86
# mkdir -p jniLibs/armeabi

# Relative paths won't work here
ln -sf /Users/bruno/git/Ancora/greetings/target/i686-linux-android/release/libgreetings.so jniLibs/x86/libgreetings.so
ln -sf /Users/bruno/git/Ancora/greetings/target/aarch64-linux-android/release/libgreetings.so jniLibs/arm64/libgreetings.so
# ln -sf /Users/bruno/git/Ancora/greetings/target/armv7-linux-androideabi/release/libgreetings.so jniLibs/armeabi/libgreetings.so