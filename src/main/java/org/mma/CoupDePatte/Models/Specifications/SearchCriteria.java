package org.mma.CoupDePatte.Models.Specifications;

public class SearchCriteria {

        private String key;  // Champ sur lequel appliquer le filtre
        private SearchOperation operation; // Opération de comparaison
        private Object value; // Valeur utilisée pour la comparaison

        // Constructeur
        public SearchCriteria(String key, SearchOperation operation, Object value) {
            this.key = key;
            this.operation = operation;
            this.value = value;
        }

        // Getters et Setters
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public SearchOperation getOperation() {
            return operation;
        }

        public void setOperation(SearchOperation operation) {
            this.operation = operation;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

}
