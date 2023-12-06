
var expandedBounds;

function updateExpandedBounds(map) {
    var bounds = map.getBounds();
    var sw = bounds.getSouthWest();
    var ne = bounds.getNorthEast();
    // Expand bounds by 30%
    var latDiff = (ne.getLat() - sw.getLat()) * 0.3;
    var lngDiff = (ne.getLng() - sw.getLng()) * 0.3;
    expandedBounds = new kakao.maps.LatLngBounds(
        new kakao.maps.LatLng(sw.getLat() - latDiff, sw.getLng() - lngDiff),
        new kakao.maps.LatLng(ne.getLat() + latDiff, ne.getLng() + lngDiff)
    );
}

function isWithinExpandedBounds(lat, lng) {
    var position = new kakao.maps.LatLng(lat, lng);
    return expandedBounds.contains(position);
}

function shouldFetchNewMarkers(map) {
    var center = map.getCenter();
    return !isWithinExpandedBounds(center.getLat(), center.getLng());
}

// Export the functions to be used in other scripts
export { updateExpandedBounds, shouldFetchNewMarkers };
