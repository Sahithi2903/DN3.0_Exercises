public class BuilderPatternExample {

    public static class Computer {
        private String CPU;
        private String RAM;
        private String Storage;
        private String GraphicsCard;
        private String OS;

        private Computer(Builder builder) {
            this.CPU = builder.CPU;
            this.RAM = builder.RAM;
            this.Storage = builder.Storage;
            this.GraphicsCard = builder.GraphicsCard;
            this.OS = builder.OS;
        }

        @Override
        public String toString() {
            return "Computer [CPU=" + CPU + ", RAM=" + RAM + ", Storage=" + Storage
                    + ", GraphicsCard=" + GraphicsCard + ", OS=" + OS + "]";
        }

        public static class Builder {
            private String CPU;
            private String RAM;
            private String Storage;
            private String GraphicsCard;
            private String OS;

            public Builder setCPU(String CPU) {
                this.CPU = CPU;
                return this;
            }

            public Builder setRAM(String RAM) {
                this.RAM = RAM;
                return this;
            }

            public Builder setStorage(String Storage) {
                this.Storage = Storage;
                return this;
            }

            public Builder setGraphicsCard(String GraphicsCard) {
                this.GraphicsCard = GraphicsCard;
                return this;
            }

            public Builder setOS(String OS) {
                this.OS = OS;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }
    }

    public static void main(String[] args) {
        Computer basicComputer = new Computer.Builder()
                .setCPU("Intel i5")
                .setRAM("8GB")
                .setStorage("1TB SSD")
                .build();

        System.out.println("Basic Computer: " + basicComputer);

        Computer gamingComputer = new Computer.Builder()
                .setCPU("Intel i9")
                .setRAM("16GB")
                .setStorage("1TB SSD")
                .setGraphicsCard("NVIDIA GTX 3080")
                .setOS("Windows 11")
                .build();

        System.out.println("Gaming Computer: " + gamingComputer);
    }
}
