<template>
    <div>
        this.coordinates.lat
    </div>
</template>

<script>
export default {
    data() {
        return {
            coordinates: {
                lat: 0,
                lng: 0 
            }
        }
    },
    created() {
        this.$getLocation({})
        .then(coordinates => {
            this.coordinates = coordinates;
        }); 
    }
}
</script>