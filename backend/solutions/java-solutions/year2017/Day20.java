package year2017;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 {
    private static class Particle {
        int number;
        Vector position;
        Vector velocity;
        Vector acceleration;

        public Particle(int number, Vector position, Vector velocity, Vector acceleration) {
            this.number = number;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

        private void move() {
            this.velocity.add(this.acceleration);
            this.position.add(this.velocity);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Particle particle)) return false;
            return number == particle.number && Objects.equals(position, particle.position) && Objects.equals(velocity, particle.velocity) && Objects.equals(acceleration, particle.acceleration);
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, position, velocity, acceleration);
        }
    }

    private static class Vector {
        int x;
        int y;
        int z;

        public Vector(String vector) {
            String[] splitVector = vector
                    .substring(2)
                    .replace("<", "")
                    .replace(">", "")
                    .split(",");

            this.x = Integer.parseInt(splitVector[0]);
            this.y = Integer.parseInt(splitVector[1]);
            this.z = Integer.parseInt(splitVector[2]);
        }

        public int length() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        public void add(Vector other) {
            this.x += other.x;
            this.y += other.y;
            this.z += other.z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vector vector)) return false;
            return x == vector.x && y == vector.y && z == vector.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    public static void main(String[] args) {
        //Part one
        /**
         * General: this one with the lowest acceleration (vector length) is the answer
         * Problem: several particles with lowest acceleration exists, then lowest velocity, then lowest position
         *
         */
        List<String> input = Utils.readLines("20.txt");

        List<Particle> particles = IntStream.range(0, input.size())
                .mapToObj(index -> {
                    String particle = input.get(index);

                    String[] splitParticle = particle.split(", ");
                    return new Particle(index,
                            new Vector(splitParticle[0]),
                            new Vector(splitParticle[1]),
                            new Vector(splitParticle[2])
                    );
                })
                .toList();

        Particle closestParticle = particles.get(0);
        for (Particle particle : particles) {
            if (particle.acceleration.length() < closestParticle.acceleration.length()
                    || particle.acceleration.length() == closestParticle.acceleration.length()
                        && particle.velocity.length() < closestParticle.velocity.length()
                    || particle.acceleration.length() == closestParticle.acceleration.length()
                        && particle.velocity.length() == closestParticle.velocity.length()
                        && particle.position.length() < closestParticle.position.length()) {
                closestParticle = particle;
            }
        }

        System.out.println(closestParticle.number);


        //Part two
        int ticksSinceChange = 0;

        while (ticksSinceChange < 100) {
            int size = particles.size();
            particles.forEach(Particle::move);

            Map<Vector, Long> frequencyMap = particles.stream()
                    .collect(Collectors.groupingBy(particle -> particle.position, Collectors.counting()));
            particles = particles.stream()
                    .filter(particle -> frequencyMap.get(particle.position) == 1)
                    .toList();

            ticksSinceChange = size == particles.size() ? ticksSinceChange + 1 : 0;
        }

        System.out.println(particles.size());
    }
}
